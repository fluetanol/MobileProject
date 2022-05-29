package com.example.mobileproject.dataclass

import java.io.Serializable

data class Forestdata  (val num:String, val name:String, val description:String, val locx:String,val locy:String, val way:String, val subway:String, val time:String, var font:Int):Serializable

//num: 번호
//name: 산책로명
//Description: 산책로설명
//locx: 산책로 위치 x좌표
//locy: 산책로 위치 y좌표
//way: 산책로 오시는길
//subway: 산책로 근처 지하철
//time :산책 소요시간
//font: 출력할 글씨 스타일
//forest와 관련된 정보를 담는 data class
