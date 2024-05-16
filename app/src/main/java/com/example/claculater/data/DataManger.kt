package com.example.claculater.data

object DataManger {
    var userPassword:Int? = null
    private val appList = mutableListOf<App>()
    val apps  : List<App>
        get() = appList

    fun addApp(newApp:App){
        appList.add(newApp)
    }

    fun setAppsData(){
        appList.apply { this.add(App("Behance", true, "Picture"))
            this.add(App("Behance", true, "Picture"))
            this.add(App("Behance", true, "Picture"))
            this.add(App("Behance", true, "Picture"))
            this.add(App("Behance", true, "Picture"))
            this.add(App("Behance", true, "Picture"))
            this.add(App("Behance", true, "Picture"))
            this.add(App("Behance", true, "Picture"))


        }
    }
}