package net.propromp.neocommander.api.annotation

import net.propromp.neocommander.api.CommandManager
import net.propromp.neocommander.api.NeoCommand
import net.propromp.neocommander.api.NeoCommandContext
import net.propromp.neocommander.api.argument.NeoArgument
import net.propromp.neocommander.api.builder.CommandBuilder
import java.lang.reflect.Method

/**
 * Annotation Manager
 *
 * @property commandManager command manager
 */
class AnnotationManager(val commandManager: CommandManager) {
    /**
     * register a command
     *
     * @param instance instance of the annotation command class
     */
    fun register(instance: Any) = commandManager.register(parse(instance))

    /**
     * parse annotation command into neo command
     *
     * @param instance instance of the annotation command class
     * @return neo command
     */
    fun parse(instance: Any): NeoCommand {
        val clazz = instance.javaClass
        val rootCommandAnnotation = clazz.getAnnotation(Command::class.java) ?: throw IllegalArgumentException()

        var rootBuilder = CommandBuilder(rootCommandAnnotation.name)
            .aliases(*rootCommandAnnotation.aliases)
            .description(rootCommandAnnotation.description)
            .requiresSender(rootCommandAnnotation.senderType.java)
        if (rootCommandAnnotation.permission != "") {
            rootBuilder = rootBuilder.requiresPermission(rootCommandAnnotation.permission)
        }

        clazz.methods.forEach { method ->
            method.getAnnotation(Command::class.java)?.let { commandAnnotation ->
                rootBuilder = rootBuilder.appendChildren(parseMethod(commandAnnotation, instance, method))
            }

            method.getAnnotation(Root::class.java)?.let { _ ->
                rootBuilder = rootBuilder.appendParallelCommands(parseMethod(rootCommandAnnotation, instance, method))
            }
        }

        clazz.classes.forEach { subClass ->
            subClass.getAnnotation(Command::class.java)?.let {
                rootBuilder = rootBuilder.appendChildren(parse(subClass.newInstance()))
            }
        }
        return rootBuilder.build()
    }

    /**
     * parse method into neo command
     *
     * @param instance instance of the annotation command class
     * @param method method
     * @return neo command
     */
    fun parseMethod(annotation: Command, instance: Any, method: Method): NeoCommand {
        var builder = CommandBuilder(annotation.name)
            .aliases(*annotation.aliases)
            .description(annotation.description)
            .requiresSender(annotation.senderType.java)
        if (annotation.permission != "") {
            builder = builder.requiresPermission(annotation.permission)
        }

        // parse arguments
        val parameters = getParameters(method)

        // arguments
        builder = builder.arguments(*parameters.filter { it.type == ParameterType.ARGUMENT }
            .map { it.argument!! as NeoArgument<Any, Any> }.toTypedArray())

        // executes
        builder = builder.executes(getFunction(instance, method, parameters))

        return builder.build()
    }

    /**
     * get function
     *
     * @param instance instance of the annotation command class
     * @param method method
     * @param parameters list of the parameter info
     * @return function
     */
    fun getFunction(instance: Any, method: Method, parameters: List<ParameterInfo>): (NeoCommandContext) -> Unit {
        return { context ->
            val arguments = mutableListOf<Any>()
            parameters.forEach {
                arguments.add(
                    when (it.type) {
                        ParameterType.ARGUMENT -> context.getArgument(it.name, Any::class.java)
                        ParameterType.SENDER -> context.source.sender
                        ParameterType.CONTEXT -> context
                        ParameterType.SOURCE -> context.source
                    } as Any
                )
            }
            method.invoke(instance, *arguments.toTypedArray())
        }
    }

    /**
     * get a list of parameter info
     *
     * @param method method
     * @return list of parameter info
     */
    fun getParameters(method: Method): List<ParameterInfo> {
        val parameterInfoList = mutableListOf<ParameterInfo>()
        method.parameters.forEach { parameter ->
            val name = parameter.name
            var parameterType: ParameterType? = null
            var argument: NeoArgument<Any, Any>? = null
            parameter.annotations.forEach annotationLoop@{ annotation ->
                parameterType = when (annotation) {
                    is Sender -> ParameterType.SENDER
                    is Context -> ParameterType.CONTEXT
                    is Source -> ParameterType.SOURCE
                    else -> {// argument
                        annotation.annotationClass.java.getAnnotation(Argument::class.java)?.let { argumentAnnotation ->
                            argumentAnnotation.argumentClass.java.constructors.forEach {
                                if (it.parameterCount == 1 && it.parameters[0].type == String::class.java) {
                                    argument = it.newInstance(parameter.name) as NeoArgument<Any, Any>
                                } else if (it.parameterCount == 2 && it.parameters[0].type == String::class.java && it.parameters[1].type == annotation.annotationClass.java) {
                                    argument = it.newInstance(
                                        parameter.name,
                                        annotation
                                    ) as NeoArgument<Any, Any>
                                }
                            }
                            if (argument != null) {
                                ParameterType.ARGUMENT
                            } else {
                                throw IllegalArgumentException()
                            }
                        } ?: return@annotationLoop
                    }
                }
            }
            parameterInfoList.add(
                ParameterInfo(
                    name,
                    parameterType ?: throw IllegalArgumentException("Illegal parameter"),
                    argument
                )
            )
        }
        return parameterInfoList
    }
}