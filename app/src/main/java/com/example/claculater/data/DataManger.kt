package com.example.claculater.data

import android.util.Log
import com.example.claculater.data.DataManger.appList

object DataManger {
    var userPassword:Int? = null
    private val appList = mutableListOf(App("Pinterest", true, "https://d1.awsstatic.com/logos/customers/pinterest_logo_banner_3.1a80b27d24a3b9df3665d586ece0bb52d66df560.png"),
    App("Behance", true, "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTPeBO7npbR_GACOgsEP_ssO4YjN9GvBYHZV8zltNvggw&s"),
    App("Facebook", true, "https://cdn.logojoy.com/wp-content/uploads/20230921104408/Facebook-logo-600x319.png"),
    App("YouTube", true, "https://yt3.googleusercontent.com/kOCTSbht6B3U0BN59mAjJ1-7jIst_tvvw1gQ5u2iyd4uw2mhEp-EHrzs7Fa0OTc0M41dsmC2Fw=s900-c-k-c0x00ffffff-no-rj"),
    App("LinkedIn", true, "https://img.freepik.com/vektoren-premium/linkedin-symbol-im-papierschnitt-stil-symbole-fuer-soziale-medien_505135-239.jpg"),
    App("Messenger", true, "https://upload.wikimedia.org/wikipedia/commons/thumb/b/be/Facebook_Messenger_logo_2020.svg/2048px-Facebook_Messenger_logo_2020.svg.png"),
    App("StackOverflow", true, "https://media.wired.com/photos/5926db217034dc5f91becd6b/master/w_2560%2Cc_limit/so-logo-s.jpg"),
    App("Instagram", true, "https://upload.wikimedia.org/wikipedia/commons/thumb/9/95/Instagram_logo_2022.svg/1200px-Instagram_logo_2022.svg.png"),
    )
    val apps  : List<App>
        get() = appList.toList()
    
    
    fun addApp(newApp:App){
        appList.add(newApp)
        Log.i("HH",appList.toString())
    }
    

    
}