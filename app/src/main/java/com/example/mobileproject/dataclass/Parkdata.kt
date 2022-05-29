package com.example.mobileproject.dataclass

import java.io.Serializable

class Parkdata(val num:String, val name:String, val description:String, var locx:String,var locy:String, var way:String,var font:Int): Serializable
{}
//num: 번호
//name: 산책로명
//Description: 산책로설명
//locx: 산책로 위치 x좌표
//locy: 산책로 위치 y좌표
//way: 산책로 오시는길
//font: 출력할 글씨 스타일
//Park와 관련된 정보를 담는 data class
