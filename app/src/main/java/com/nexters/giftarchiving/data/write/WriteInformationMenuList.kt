package com.nexters.giftarchiving.data.write

import com.nexters.giftarchiving.R

internal object WriteInformationMenuList {
    val purposeMenuList = listOf(
        WritePurposeMenu("생일", R.drawable.ic_birthday_b, R.drawable.ic_birthday),
        WritePurposeMenu("기념일", R.drawable.ic_anniversary_b, R.drawable.ic_anniversary),
        WritePurposeMenu("결혼", R.drawable.ic_wedding_b, R.drawable.ic_wedding),
        WritePurposeMenu("취업", R.drawable.ic_getajob_b, R.drawable.ic_getajob),
        WritePurposeMenu("명절", R.drawable.ic_holiday_b, R.drawable.ic_holiday),
        WritePurposeMenu("졸업", R.drawable.ic_graduation_b, R.drawable.ic_graduation),
        WritePurposeMenu("사과", R.drawable.ic_apology_b, R.drawable.ic_apology),
        WritePurposeMenu("감사", R.drawable.ic_appreciation_b, R.drawable.ic_appreciation),
        WritePurposeMenu("응원", R.drawable.ic_cheer_b, R.drawable.ic_cheer),
        WritePurposeMenu("집들이", R.drawable.ic_housewarming_b, R.drawable.ic_housewarming),
        WritePurposeMenu("그냥", R.drawable.ic_just_b, R.drawable.ic_just),
        WritePurposeMenu("기타", R.drawable.ic_etc_b, R.drawable.ic_etc),
    ).map { WritePurposeMenuItem(it) }

    val categoryMenuList = listOf(
        WriteCategoryMenu("디지털", R.drawable.ic_digital_b, R.drawable.ic_digital),
        WriteCategoryMenu("식품", R.drawable.ic_groceries_b, R.drawable.ic_groceries),
        WriteCategoryMenu("리빙", R.drawable.ic_living_b, R.drawable.ic_living),
        WriteCategoryMenu("펫", R.drawable.ic_pet_b, R.drawable.ic_pet),
        WriteCategoryMenu("유아동", R.drawable.ic_baby_b, R.drawable.ic_baby),
        WriteCategoryMenu("상품권", R.drawable.ic_gift_card_b, R.drawable.ic_gift_card),
        WriteCategoryMenu("스포츠", R.drawable.ic_sports_b, R.drawable.ic_sports),
        WriteCategoryMenu("패션", R.drawable.ic_fashion_b, R.drawable.ic_fashion),
        WriteCategoryMenu("화장품", R.drawable.ic_cosmetic_b, R.drawable.ic_cosmetic),
        WriteCategoryMenu("모바일교환권", R.drawable.ic_mcoupon_b, R.drawable.ic_mcoupon),
        WriteCategoryMenu("컬처", R.drawable.ic_culture_b, R.drawable.ic_culture),
        WriteCategoryMenu("기타", R.drawable.ic_etc_b, R.drawable.ic_etc),
    ).map { WriteCategoryMenuItem(it) }

    val sendEmotionList = listOf(
        WriteEmotionMenu("응원해", R.drawable.ic_emoji_cheer_b, R.drawable.ic_emoji_cheer),
        WriteEmotionMenu("미안해", R.drawable.ic_emoji_sorry_b, R.drawable.ic_emoji_sorry),
        WriteEmotionMenu("나최고지", R.drawable.ic_emoji_best_b, R.drawable.ic_emoji_best),
        WriteEmotionMenu("축하해", R.drawable.ic_emoji_celebration_b, R.drawable.ic_emoji_celebration)
    ).map { WriteEmotionMenuItem(it) }

    val receiveEmotionList = listOf(
        WriteEmotionMenu("센스최고", R.drawable.ic_emoji_sense_b, R.drawable.ic_emoji_sense),
        WriteEmotionMenu("사랑해", R.drawable.ic_emoji_love_b, R.drawable.ic_emoji_love),
        WriteEmotionMenu("감동이야", R.drawable.ic_emoji_touch_b, R.drawable.ic_emoji_touch),
        WriteEmotionMenu("놀라워", R.drawable.ic_emoji_surprisal_b, R.drawable.ic_emoji_surprisal)
    ).map { WriteEmotionMenuItem(it) }
}