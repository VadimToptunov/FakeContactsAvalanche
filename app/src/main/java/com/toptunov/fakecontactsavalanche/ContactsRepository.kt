package com.toptunov.fakecontactsavalanche

import android.content.ContentProviderOperation
import android.content.Context
import android.provider.ContactsContract
import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import net.datafaker.Faker

/**
 * Repository for creating fake contacts using suspend functions for async work
 */
class ContactsRepository(private val context: Context) {

    private val faker = Faker()
    suspend fun createSingleContact(): Boolean = withContext(Dispatchers.IO) {
        try {
            val contactName = faker.name().fullName()
            val contactPhoneNumber = faker.phoneNumber().cellPhone()
            val contactCompany = faker.company().name()
            val contactJobTitle = faker.job().position()

            Log.d(TAG, "Creating contact: $contactName, $contactPhoneNumber")

            val operations = buildContactOperations(
                name = contactName,
                phoneNumber = contactPhoneNumber,
                company = contactCompany,
                jobTitle = contactJobTitle
            )

            context.contentResolver.applyBatch(ContactsContract.AUTHORITY, operations)
            true
        } catch (e: Exception) {
            Log.e(TAG, "Error creating contact", e)
            false
        }
    }
    suspend fun createMultipleContacts(
        count: Int,
        onProgress: (current: Int, total: Int) -> Unit
    ): Int = withContext(Dispatchers.IO) {
        var successCount = 0
        
        repeat(count) { index ->
            if (createSingleContact()) {
                successCount++
            }
            onProgress(index + 1, count)
        }
        
        successCount
    }

    private fun buildContactOperations(
        name: String,
        phoneNumber: String,
        company: String,
        jobTitle: String
    ): ArrayList<ContentProviderOperation> {
        val operations = ArrayList<ContentProviderOperation>()

        operations.add(
            ContentProviderOperation.newInsert(ContactsContract.RawContacts.CONTENT_URI)
                .withValue(ContactsContract.RawContacts.ACCOUNT_TYPE, null)
                .withValue(ContactsContract.RawContacts.ACCOUNT_NAME, null)
                .build()
        )

        operations.add(
            ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI)
                .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0)
                .withValue(
                    ContactsContract.Data.MIMETYPE,
                    ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE
                )
                .withValue(
                    ContactsContract.CommonDataKinds.StructuredName.DISPLAY_NAME,
                    name
                )
                .build()
        )

        operations.add(
            ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI)
                .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0)
                .withValue(
                    ContactsContract.Data.MIMETYPE,
                    ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE
                )
                .withValue(ContactsContract.CommonDataKinds.Phone.NUMBER, phoneNumber)
                .withValue(
                    ContactsContract.CommonDataKinds.Phone.TYPE,
                    ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE
                )
                .build()
        )

        operations.add(
            ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI)
                .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0)
                .withValue(
                    ContactsContract.Data.MIMETYPE,
                    ContactsContract.CommonDataKinds.Organization.CONTENT_ITEM_TYPE
                )
                .withValue(ContactsContract.CommonDataKinds.Organization.COMPANY, company)
                .withValue(ContactsContract.CommonDataKinds.Organization.TITLE, jobTitle)
                .withValue(
                    ContactsContract.CommonDataKinds.Organization.TYPE,
                    ContactsContract.CommonDataKinds.Organization.TYPE_WORK
                )
                .build()
        )

        return operations
    }

    companion object {
        private const val TAG = "ContactsRepository"
    }
}

