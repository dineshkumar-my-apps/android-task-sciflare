package com.android.assessment.sciflare.technologies.support

import com.android.assessment.sciflare.technologies.room.entity.User

interface OnClick {

    fun userEdit(list: User)

    fun userDelete(list: User)

}