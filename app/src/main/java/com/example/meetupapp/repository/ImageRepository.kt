package com.example.meetupapp.repository

import com.example.meetupapp.R
import com.example.meetupapp.database.model.ImageDatabaseEntry
import java.lang.RuntimeException
import javax.inject.Inject

class ImageRepository @Inject constructor(){

    fun getImageByID(id: String): ImageDatabaseEntry {
        val imageDatabase = getDummyImageDatabaseData()
        for ((count, image) in imageDatabase.withIndex()) {
            if (image.imageID == id) {
                return imageDatabase[count]
            }
        }
        throw RuntimeException("Image not found.")
    }

    // << Dummy Data >>
    private fun getDummyImageDatabaseData(): List<ImageDatabaseEntry> {

        val dummyDataList: MutableList<ImageDatabaseEntry> = mutableListOf()
        val imageItem01 = ImageDatabaseEntry(
            "0", R.drawable.default_profile_image
        )
        val imageItem02 = ImageDatabaseEntry(
            "1", R.drawable.default_profile_image
        )
        val imageItem03 = ImageDatabaseEntry(
            "2", R.drawable.default_profile_image
        )
        val imageItem04 = ImageDatabaseEntry(
            "3", R.drawable.default_profile_image
        )

        dummyDataList.add(imageItem01)
        dummyDataList.add(imageItem02)
        dummyDataList.add(imageItem03)
        dummyDataList.add(imageItem04)
        return dummyDataList

    }

}