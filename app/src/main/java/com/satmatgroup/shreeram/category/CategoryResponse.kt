package com.myapp.onlysratchapp.category

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName




class CategoryResponse {
    @SerializedName("id")
    @Expose
    private var id: String? = null

    @SerializedName("name")
    @Expose
    private var name: String? = null

    @SerializedName("image")
    @Expose
    private var image: String? = null

    @SerializedName("url")
    @Expose
    private var url: String? = null

    @SerializedName("createat")
    @Expose
    private var createat: String? = null

    @SerializedName("data")
    @Expose
    private var data: String? = null

    @SerializedName("referalcode")
    @Expose
    private var referalcode: Any? = null

    fun getId(): String? {
        return id
    }

    fun setId(id: String?) {
        this.id = id
    }

    fun getName(): String? {
        return name
    }

    fun setName(name: String?) {
        this.name = name
    }

    fun getImage(): String? {
        return image
    }

    fun setImage(image: String?) {
        this.image = image
    }

    fun getUrl(): String? {
        return url
    }

    fun setUrl(url: String?) {
        this.url = url
    }

    fun getCreateat(): String? {
        return createat
    }

    fun setCreateat(createat: String?) {
        this.createat = createat
    }

    fun getData(): String? {
        return data
    }

    fun setData(data: String?) {
        this.data = data
    }

    fun getReferalcode(): Any? {
        return referalcode
    }

    fun setReferalcode(referalcode: Any?) {
        this.referalcode = referalcode
    }

}