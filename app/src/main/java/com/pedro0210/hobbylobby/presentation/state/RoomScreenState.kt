package com.pedro0210.hobbylobby.presentation.state

import com.pedro0210.hobbylobby.presentation.model.RoomMember

data class RoomScreenState(
    val users: List<RoomMember> = emptyList(),
    val isJoined: Boolean = false,
    val roomName: String = "",
    val roomDescription: String = ""
) {

}
