package net.propromp.spicommander.api.argument.annotation

import net.propromp.spicommander.api.annotation.Argument
import net.propromp.spicommander.api.argument.LocationArgument

@Argument(LocationArgument::class)
annotation class LocationArgument(val type: LocationArgument.LocationType = LocationArgument.LocationType.ENTITY)
