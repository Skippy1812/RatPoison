package com.charlatano.game.entity

import com.charlatano.game.CSGO.ENTITY_SIZE
import com.charlatano.game.CSGO.clientDLL
import com.charlatano.game.CSGO.csgoEXE
import com.charlatano.game.netvars.NetVarOffsets.bIsScoped
import com.charlatano.game.netvars.NetVarOffsets.dwBoneMatrix
import com.charlatano.game.netvars.NetVarOffsets.fFlags
import com.charlatano.game.netvars.NetVarOffsets.hActiveWeapon
import com.charlatano.game.netvars.NetVarOffsets.iHealth
import com.charlatano.game.netvars.NetVarOffsets.iWeaponID
import com.charlatano.game.netvars.NetVarOffsets.lifeState
import com.charlatano.game.netvars.NetVarOffsets.nTickBase
import com.charlatano.game.netvars.NetVarOffsets.vecPunch
import com.charlatano.game.netvars.NetVarOffsets.vecVelocity
import com.charlatano.game.netvars.NetVarOffsets.vecViewOffset
import com.charlatano.game.offsets.ClientOffsets.dwEntityList
import com.charlatano.settings.HEAD_BONE
import com.charlatano.settings.SERVER_TICK_RATE
import com.charlatano.utils.Angle
import com.charlatano.utils.Vector
import com.charlatano.utils.Weapons
import com.charlatano.utils.extensions.uint
import org.jire.arrowhead.get

typealias Player = Long

fun Player.weapon(): Weapons {
	val address: Long = csgoEXE.uint(this + hActiveWeapon)
	val index = address and 0xFFF
	val base: Int = clientDLL[dwEntityList + (index - 1) * ENTITY_SIZE]
	
	var id = 42
	if (base > 0)
		id = csgoEXE[base + iWeaponID]
	
	return Weapons[id]
}

internal fun Player.flags(): Int = csgoEXE.int(this + fFlags)

internal fun Player.onGround() = flags() and 1 == 1

internal fun Player.health(): Int = csgoEXE.int(this + iHealth)

internal fun Player.dead() = try {
	(csgoEXE.byte(this + lifeState) != 0.toByte()) || health() <= 0
} catch (t: Throwable) {
	false
}

internal fun Player.punch(): Angle
		= Vector(csgoEXE.float(this + vecPunch).toDouble(), csgoEXE.float(this + vecPunch + 4).toDouble(), 0.0)

internal fun Player.viewOffset(): Angle
		= Vector(csgoEXE.float(this + vecViewOffset).toDouble(),
		csgoEXE.float(this + vecViewOffset + 4).toDouble(),
		csgoEXE.float(this + vecViewOffset + 8).toDouble())

internal fun Player.velocity(): Angle
		= Vector(csgoEXE.float(this + vecVelocity).toDouble(),
		csgoEXE.float(this + vecVelocity + 4).toDouble(),
		csgoEXE.float(this + vecVelocity + 8).toDouble())

internal fun Player.boneMatrix() = csgoEXE.uint(this + dwBoneMatrix)

internal fun Player.bone(offset: Int, boneID: Int = HEAD_BONE, boneMatrix: Long = boneMatrix())
		= csgoEXE.float(boneMatrix + ((0x30 * boneID) + offset)).toDouble()

internal fun Player.isScoped(): Boolean = csgoEXE[this + bIsScoped]

internal fun Player.time(): Double = csgoEXE.int(this + nTickBase) * (1.0 / SERVER_TICK_RATE)