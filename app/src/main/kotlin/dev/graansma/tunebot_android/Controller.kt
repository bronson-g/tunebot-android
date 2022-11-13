package dev.graansma.tunebot_android

class Controller(promotionLevel: PromotionLevel) {
    private val api = Api(promotionLevel)
    private val masterList = mutableMapOf<String, Playlist>()

    fun updateMasterList(macs: Set<String>) {
        for(mac in masterList.keys) {
            if(!macs.contains(mac)) {
                masterList.remove(mac)
            }
        }
        for(mac in macs) {
            if(!masterList.contains(mac)) {
                masterList[mac] = api.getPlaylist(mac)
            }
        }
    }
}

enum class PromotionLevel {
    DEVELOPMENT,
    PRODUCTION
}

class Playlist(val songs: Set<String>, val blacklist: Set<String>)