package com.nexters.giftarchiving.data.write

import com.nexters.giftarchiving.R

internal object WriteInformationMenuList {
    val purposeMenuList = listOf(
        WritePurposeMenu("생일", "BIRTHDAY", R.drawable.ic_birthday_b, R.drawable.ic_birthday),
        WritePurposeMenu("기념일", "ANNIVERSARY", R.drawable.ic_anniversary_b, R.drawable.ic_anniversary),
        WritePurposeMenu("결혼", "MARRIAGE", R.drawable.ic_wedding_b, R.drawable.ic_wedding),
        WritePurposeMenu("취업", "EMPLOYMENT", R.drawable.ic_getajob_b, R.drawable.ic_getajob),
        WritePurposeMenu("명절", "HOLIDAY", R.drawable.ic_holiday_b, R.drawable.ic_holiday),
        WritePurposeMenu("졸업", "STUDY", R.drawable.ic_graduation_b, R.drawable.ic_graduation),
        WritePurposeMenu("사과", "APOLOGIZE", R.drawable.ic_apology_b, R.drawable.ic_apology),
        WritePurposeMenu("감사", "THANKS", R.drawable.ic_appreciation_b, R.drawable.ic_appreciation),
        WritePurposeMenu("응원", "CHEER_UP", R.drawable.ic_cheer_b, R.drawable.ic_cheer),
        WritePurposeMenu("집들이", "HOUSES", R.drawable.ic_housewarming_b, R.drawable.ic_housewarming),
        WritePurposeMenu("그냥", "JUST", R.drawable.ic_just_b, R.drawable.ic_just),
        WritePurposeMenu("기타", "ETC", R.drawable.ic_etc_b, R.drawable.ic_etc),
    ).map { WritePurposeMenuItem(it) }

    val categoryMenuList = listOf(
        WriteCategoryMenu("디지털", "DIGITAL", R.drawable.ic_digital_b, R.drawable.ic_digital),
        WriteCategoryMenu("식품", "FOOD", R.drawable.ic_groceries_b, R.drawable.ic_groceries),
        WriteCategoryMenu("리빙", "LIVING", R.drawable.ic_living_b, R.drawable.ic_living),
        WriteCategoryMenu("펫", "PET", R.drawable.ic_pet_b, R.drawable.ic_pet),
        WriteCategoryMenu("유아동", "BABY", R.drawable.ic_baby_b, R.drawable.ic_baby),
        WriteCategoryMenu("상품권", "VOUCHER", R.drawable.ic_gift_card_b, R.drawable.ic_gift_card),
        WriteCategoryMenu("스포츠", "SPORTS", R.drawable.ic_sports_b, R.drawable.ic_sports),
        WriteCategoryMenu("패션", "FASHION", R.drawable.ic_fashion_b, R.drawable.ic_fashion),
        WriteCategoryMenu("화장품", "BEAUTY", R.drawable.ic_cosmetic_b, R.drawable.ic_cosmetic),
        WriteCategoryMenu("모바일교환권", "GIFT_CARD", R.drawable.ic_mcoupon_b, R.drawable.ic_mcoupon),
        WriteCategoryMenu("컬처", "CULTURE", R.drawable.ic_culture_b, R.drawable.ic_culture),
        WriteCategoryMenu("기타", "ETC", R.drawable.ic_etc_b, R.drawable.ic_etc),
    ).map { WriteCategoryMenuItem(it) }

    val sendEmotionList = listOf(
        WriteEmotionMenu("응원해", "CHEER", R.drawable.ic_emoji_cheer_b, R.drawable.ic_emoji_cheer),
        WriteEmotionMenu("미안해", "SORRY", R.drawable.ic_emoji_sorry_b, R.drawable.ic_emoji_sorry),
        WriteEmotionMenu("나최고지", "BOAST",R.drawable.ic_emoji_best_b, R.drawable.ic_emoji_best),
        WriteEmotionMenu("축하해", "CONGRATULATIONS", R.drawable.ic_emoji_celebration_b, R.drawable.ic_emoji_celebration)
    ).map { WriteEmotionMenuItem(it) }

    val receiveEmotionList = listOf(
        WriteEmotionMenu("센스최고", "NICE_SENSE", R.drawable.ic_emoji_sense_b, R.drawable.ic_emoji_sense),
        WriteEmotionMenu("사랑해", "LOVE_YOU", R.drawable.ic_emoji_love_b, R.drawable.ic_emoji_love),
        WriteEmotionMenu("감동이야", "IMPRESSION",R.drawable.ic_emoji_touch_b, R.drawable.ic_emoji_touch),
        WriteEmotionMenu("놀라워", "AMAZING", R.drawable.ic_emoji_surprisal_b, R.drawable.ic_emoji_surprisal)
    ).map { WriteEmotionMenuItem(it) }

    fun findPurpose(engName: String): WritePurposeMenu =
        purposeMenuList
            .find { it.purposeMenu.titleEng == engName }
            ?.purposeMenu
            ?: WritePurposeMenu()

    fun findCategory(engName: String): WriteCategoryMenu =
        categoryMenuList
            .find { it.categoryMenu.titleEng == engName }
            ?.categoryMenu
            ?: WriteCategoryMenu()

    fun findEmotion(engName: String, isReceive: Boolean): WriteEmotionMenu =
        when (isReceive) {
            true -> receiveEmotionList
            false -> sendEmotionList
        }.find { it.emotionMenu.titleEng == engName }
            ?.emotionMenu
            ?: WriteEmotionMenu()
}