package com.trx.database

import com.trx.R
import com.trx.models.GunsModel

object Inventory {

fun defaultInventory () : ArrayList<GunsModel>{

    val gunsList = ArrayList<GunsModel>()

    val m416 = GunsModel(1,
"M416",
        "3x",
        40,
        R.drawable.im_m416
        )
    gunsList.add(m416)

    val ak47 = GunsModel(2,
        "AK47",
        "Iron-Sight",
        40,
        R.drawable.im_ak47
    )
    gunsList.add(ak47)

    val ak105 = GunsModel(3,
        "AK105",
        "Red-Dot",
        40,
        R.drawable.im_ak105
        )
    gunsList.add(ak105)

    val m16 = GunsModel(4,
        "M16",
        "4x",
        50,
        R.drawable.im_m16
        )
    gunsList.add(m16)

    val m762 = GunsModel(5,
        "M762",
        "Red-Dot",
        40,
        R.drawable.im_m762
        )
    gunsList.add(m762)

    val scarl = GunsModel(6,
        "SCAR-L",
        "6x",
        40,
        R.drawable.im_scarl
        )
    gunsList.add(scarl)

    val glock = GunsModel(7,
        "GLOCK",
        "Iron-Sight",
        40,
        R.drawable.im_glock
        )

    return gunsList
}

}