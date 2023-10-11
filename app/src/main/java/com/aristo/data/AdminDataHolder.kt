package com.aristo.data

import com.aristo.model.Admin

class AdminDataHolder private constructor(){

    var admin : Admin? = null

    companion object {
        val instance: AdminDataHolder = AdminDataHolder()
    }

}