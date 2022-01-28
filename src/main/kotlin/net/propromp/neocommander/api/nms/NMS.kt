package net.propromp.neocommander.api.nms

class NMS(var clazz: Class<out Any>) {
    constructor(clazz: Class<out Any>, instance: Any) : this(clazz) {
        this.instance = instance
    }

    lateinit var instance: Any

    fun construct(vararg args: Any) = constructBySelectingTypes(args.map { it::class.javaPrimitiveType ?: it.javaClass }.toTypedArray(), arrayOf(args))
    fun constructBySelectingTypes(argsType: Array<Class<out Any>>, args: Array<Any>): Any {
        val constructor = clazz.getDeclaredConstructor(*argsType)
        constructor.isAccessible = true
        instance = constructor.newInstance(*args)
        return instance
    }

    fun invokeMethod(name: String, vararg args: Any) =
        invokeMethodBySelectingTypes(name, args, args.map { it::class.javaPrimitiveType ?: it.javaClass }.toTypedArray())

    fun invokeMethodBySelectingTypes(name: String, args: Array<out Any?>, argsTypes: Array<Class<out Any>>): Any? {
        val method = clazz.getDeclaredMethod(name, *argsTypes)
        method.isAccessible = true
        return method.invoke(instance, *args)
    }

    fun getField(name: String, clazz: Class<out Any> = this.clazz): Any? {
        val field = clazz.getDeclaredField(name)
        field.isAccessible = true
        return field.get(instance)
    }

    fun invokeStaticMethod(name: String, vararg args: Any): Any? =
        invokeStaticMethodBySelectingTypes(name, args, args.map { it.javaClass }.toTypedArray())

    fun invokeStaticMethodBySelectingTypes(
        name: String,
        args: Array<out Any?>,
        argsTypes: Array<Class<out Any>>
    ): Any? {
        val method = clazz.getDeclaredMethod(name, *argsTypes)
        method.isAccessible = true
        return method.invoke(null, *args)
    }

    fun getStaticField(name: String, clazz: Class<out Any> = this.clazz): Any? {
        val field = clazz.getDeclaredField(name)
        field.isAccessible = true
        return field.get(null)
    }
}