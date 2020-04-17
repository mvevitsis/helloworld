definition(
  name: "Hello World",
  namespace: "mvevitsis",
  author: "Matvei Vevitsis", 
  description: "Speak hello world on a connected speaker when a switch is turned on",
  category: "Convenience",
  iconUrl: "https://s3.amazonaws.com/smartapp-icons/Convenience/Cat-Convenience.png",
  iconX2Url: "https://s3.amazonaws.com/smartapp-icons/Convenience/Cat-Convenience%402x.png"
)

preferences {
  page(name: "mainPage", title: "Speak hello world on a connected speaker when a switch is turned on", install: true, uninstall: true)
}

def mainPage() {
 dynamicPage(name: "mainPage") {
  section("Input"){
   input "mySwitch", "capability.switch", title: "Select a switch", required: false, multiple: true
  }
  section("Output"){
   input "audioDevices", "capability.audioNotification", title: "Select a speaker", required: false, multiple: true
   input "sendPushMessage", "enum", title: "Send a push notification", options: ["Yes", "No"], defaultValue: "No", required: true
  }
 }
}

def installed(){
 subscribeToEvents()
}

def updated(){
 unsubscribe()
 subscribeToEvents()
}

def subscribeToEvents(){
 subscribe(mySwitch, "switch.on", eventHandler)
}

def eventHandler(evt){
 sendMessage(evt)
}

private sendMessage(evt){
 String msg = "Hello World"
  if(sendPushMessage == "Yes") {
     sendPush(msg)
  }
  if(audioDevices){
  	audioDevices?.each { audioDevice -> 
       if (audioDevice.hasCommand("playText")) { //Check if speaker supports TTS 
             audioDevice.playText(msg)
       } else {
       if (audioDevice.hasCommand("speak")) { //Check if speaker supports speech synthesis  
       		 audioDevice.speak(msg)
       } else {
             audioDevice.playTrack(textToSpeech(msg)?.uri) 
         }
       } 
  
    }
  }
}

