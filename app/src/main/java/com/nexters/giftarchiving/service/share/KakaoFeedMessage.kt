package com.nexters.giftarchiving.service.share

import com.kakao.sdk.template.model.Content
import com.kakao.sdk.template.model.FeedTemplate
import com.kakao.sdk.template.model.Link

internal object KakaoFeedMessage {
    private const val title = "\uD83C\uDF81기프트집 선물도착\uD83C\uDF81\n"

    fun getFeed(url: String, name: String, isReceive: Boolean): FeedTemplate {
        val description = when (isReceive) {
            true -> "${name}님이 나에게 보낸 선물이 도착했어요!"
            false -> "${name}님에게 보낸 선물이 도착했어요!"
        }
        return FeedTemplate(
            content = Content(
                title = title,
                description = description,
                imageUrl = url,
                link = Link()
            ),
            buttonTitle = "구경하기"
        )
    }
}