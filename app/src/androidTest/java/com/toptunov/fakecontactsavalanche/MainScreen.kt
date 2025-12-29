package com.toptunov.fakecontactsavalanche

import com.kaspersky.kaspresso.screens.KScreen
import io.github.kakaocup.kakao.edit.KEditText
import io.github.kakaocup.kakao.progress.KProgressBar
import io.github.kakaocup.kakao.text.KButton
import io.github.kakaocup.kakao.text.KTextView

object MainScreen : KScreen<MainScreen>() {

    override val layoutId: Int = R.layout.activity_main
    override val viewClass: Class<*> = MainActivity::class.java

    val titleText = KTextView { withId(R.id.titleText) }
    
    val inputLayout = KTextView { withId(R.id.inputLayout) }
    
    val inputField = KEditText { withId(R.id.contactsQuantity) }
    
    val generateButton = KButton { withId(R.id.generate_button) }
    
    val cancelButton = KButton { withId(R.id.cancel_button) }
    
    val progressCard = KTextView { withId(R.id.progressCard) }
    
    val progressText = KTextView { withId(R.id.progressText) }
    
    val progressBar = KProgressBar { withId(R.id.progressBar) }
    
    val percentageText = KTextView { withId(R.id.percentageText) }
    
    val statusText = KTextView { withId(R.id.statusText) }
}

