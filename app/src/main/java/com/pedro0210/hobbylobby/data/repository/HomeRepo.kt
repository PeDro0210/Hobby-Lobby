package com.pedro0210.hobbylobby.data.repository

import com.google.firebase.firestore.FirebaseFirestore
import com.pedro0210.hobbylobby.data.datastore.UserPreferences
import com.pedro0210.hobbylobby.presentation.model.Community
import com.pedro0210.hobbylobby.presentation.model.CommunityType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.tasks.await

class HomeRepo(
    private var userPreferences: UserPreferences, //for fetching the id
) {

    private val firestore = FirebaseFirestore.getInstance()

    suspend fun getCountries(): Flow<List<Community>> {
        try {
            val bigCommunitiesDoc = firestore.collection("big_communities").get().await()
            return flowOf(
                bigCommunitiesDoc.documents.map { doc ->
                    Community(
                        title = doc.getString("name") ?: "",
                        description = doc.getString("description") ?: "",
                        image = doc.getString("pfp") ?: "",
                        id = doc.id,
                        type = CommunityType.bigCommunity,
                        partOfCommunity = true
                    )
                }
            )
        } catch (e: Exception) {
            println(e.message)
        }
        return flowOf(emptyList())
    }

    suspend fun getOwnRooms(): Flow<List<Community>> {
        //TODO: call the repo with the firebase implementation
        try{
            val id = userPreferences.getId().first()
            val userRef = firestore.collection("users").document(id)

            // Now query the rooms collection to see if the user is part of any room
            val roomsQuery = firestore.collectionGroup("rooms")
                .whereArrayContains("users", userRef) // Check if the userRef is in the 'users' array

            val communitiesDoc = roomsQuery.get().await()

            // Check results
            if (communitiesDoc.isEmpty) {
                println("No rooms found for this user")
            } else {
                println("Found rooms: ${communitiesDoc.documents}")
            }
            return flowOf(
                communitiesDoc.documents.map { doc ->
                    Community(
                        title = doc.getString("name") ?: "",
                        description = doc.getString("description") ?: "",
                        image = doc.getString("pfp") ?: "",
                        id = doc.id,
                        type = CommunityType.rooms,
                        partOfCommunity = true
                    )
                }
            )


        } catch (e: Exception) {
            println(e.message)
        }
        return flowOf(emptyList())
    }

}