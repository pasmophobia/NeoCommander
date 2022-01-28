package net.propromp.neocommander.api.argument.annotation

import net.propromp.neocommander.api.annotation.Argument
import net.propromp.neocommander.api.argument.LocationArgument

@Argument(LocationArgument::class)
annotation class LocationArgument(val type: LocationArgument.LocationType = LocationArgument.LocationType.ENTITY)
