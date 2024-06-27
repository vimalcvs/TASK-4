package com.vimal.healthy.models

class CategoryButtonModel {

    private var name: String? = null
    private var image: Int? = null
    private var num: Int? = null

    constructor(name: String, image: Int, num: Int) {
        this.name = name
        this.image = image
        this.num = num
    }

    fun getName(): String? {
        return name
    }

    fun getImage(): Int? {
        return image
    }

    fun getNum(): Int? {
        return num
    }

}



