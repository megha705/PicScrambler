package vardansharma.me.picscrambler.data

// choose to parse only the relevant fields
data class PhotoWrapper(val title: String?, val items: List<Item>?)

data class Item(val title: String?, val media: Media?, val description: String?)
data class Media(val m: String?)

