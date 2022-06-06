package ayds.lisboa.songinfo.moredetails.view

sealed class MoreDetailsUiEvent {
    object Search: MoreDetailsUiEvent()
    object OpenSource: MoreDetailsUiEvent()
}