package com.toptunov.fakecontactsavalanche

import android.content.ContentProviderOperation
import android.provider.ContactsContract
import android.content.Context
import android.util.Log
import net.datafaker.Faker

class FakeAgent {

    fun generateFakeContactsData(context: Context) {

        val faker = Faker()
        val contactName = faker.name().fullName().toString()
        val contactPhoneNumber = faker.phoneNumber().cellPhone()
        val contactCompany = faker.company().name().toString()
        val contactJobTitle = faker.job().position()
        val contact = String.format("Contact data: $contactName , $contactPhoneNumber , $contactCompany , $contactJobTitle")
        Log.d("[AppDebugLog]", contact)
        val contactData = ArrayList<ContentProviderOperation>()

        contactData.add(
            ContentProviderOperation.newInsert(ContactsContract.RawContacts.CONTENT_URI)
                .withValue(ContactsContract.RawContacts.ACCOUNT_TYPE, null)
                .withValue(ContactsContract.RawContacts.ACCOUNT_NAME, null)
                .build()
        )

        contactData.add(ContentProviderOperation.newInsert(
            ContactsContract.Data.CONTENT_URI)
            .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0)
            .withValue(ContactsContract.Data.MIMETYPE,
                ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE)
            .withValue(
                ContactsContract.CommonDataKinds.StructuredName.DISPLAY_NAME,
                contactName).build()
        )

        contactData.add(ContentProviderOperation.
        newInsert(ContactsContract.Data.CONTENT_URI)
            .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0)
            .withValue(ContactsContract.Data.MIMETYPE,
                ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE)
            .withValue(ContactsContract.CommonDataKinds.Phone.NUMBER, contactPhoneNumber)
            .withValue(ContactsContract.CommonDataKinds.Phone.TYPE,
                ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE)
            .build()
        )

        contactData.add(ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI)
            .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0)
            .withValue(ContactsContract.Data.MIMETYPE,
                ContactsContract.CommonDataKinds.Organization.CONTENT_ITEM_TYPE)
            .withValue(ContactsContract.CommonDataKinds.Organization.COMPANY, contactCompany)
            .withValue(ContactsContract.CommonDataKinds.Organization.TYPE, ContactsContract.CommonDataKinds.Organization.TYPE_WORK)
            .withValue(ContactsContract.CommonDataKinds.Organization.TITLE, contactJobTitle)
            .withValue(ContactsContract.CommonDataKinds.Organization.TYPE, ContactsContract.CommonDataKinds.Organization.TYPE_WORK)
            .build())

        try {
            context.contentResolver.applyBatch(ContactsContract.AUTHORITY, contactData)
        }catch (e : Exception){
            Log.e("[AppDebugLog]", e.toString())
        }
    }
}