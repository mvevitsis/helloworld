
definition(
	name: "Hello World",
	namespace: "mvevitsis",
	author: "Matvei Vevitsis",
	description: "Speak Hello World on a connected speaker when a switch is turned on",
	category: "Convenience",
	iconUrl: "https://s3.amazonaws.com/smartapp-icons/Partner/sonos.png",
	iconX2Url: "https://s3.amazonaws.com/smartapp-icons/Partner/sonos@2x.png"
)

preferences {
	page(name: "mainPage", title: "Speak Hello World on a connected speaker when a switch is turned on", install: true, uninstall: true)
    }

def mainPage() {
	dynamicPage(name: "mainPage") {
			section("Input"){
            		input "mySwitch", "capability.switch", title: "Switch Turned On", required: false, multiple: true
			}
            section("Output"){
            	input "speechDevices", "capability.speechSynthesis", title: "Select a speech synthesis device", required: false, multiple: true
		    }
	}
}

def installed() {
	log.debug "Installed with settings: ${settings}"
	subscribeToEvents()
}

def updated() {
	log.debug "Updated with settings: ${settings}"
	unsubscribe()
	subscribeToEvents()
}


def subscribeToEvents() {
	subscribe(mySwitch, "switch.on", eventHandler)
    }

def eventHandler(evt) {
	log.debug "Notify got evt ${evt}"
	sendMessage(evt)
	}


private sendMessage(evt){
	String msg = "Hello World"
		if(speechDevices){
        	speechDevices.each(){
            	it.speak(msg)
            }
        }
}
        



       
       
    
