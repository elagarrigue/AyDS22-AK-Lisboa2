package ayds.lisboa.songinfo.moredetails.view

sealed class MoreDetailsUiEvent {
    object Search: MoreDetailsUiEvent()
    object OpenLastFM: MoreDetailsUiEvent()
    object OpenWikipedia: MoreDetailsUiEvent()
    object OpenNYT: MoreDetailsUiEvent()
}