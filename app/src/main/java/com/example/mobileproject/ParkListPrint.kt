package com.example.mobileproject

import android.graphics.Typeface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mobileproject.RecyclerAdapter.ForestRecyclerAdapter
import com.example.mobileproject.RecyclerAdapter.ParkRecyclerAdapter
import com.example.mobileproject.databinding.FragmentListPrintBinding
import com.example.mobileproject.dataclass.Forestdata
import com.example.mobileproject.dataclass.Parkdata
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.jsoup.Jsoup
import org.jsoup.parser.Parser

class ParkListPrint:Fragment() {
    lateinit var adapter: ParkRecyclerAdapter
    var binding: FragmentListPrintBinding?= null
    val scope = CoroutineScope(Dispatchers.IO)
    var parklist = arrayListOf<Parkdata>()
    val URLPARK = "http://openapi.seoul.go.kr:8088/4646617a476d696b313138566b6e727a/xml/SearchParkInfoService/1/300/"

    companion object{
        const val COURSEPARK_NUM = "P_IDX"                  //공원번호
        const val COURSEPARK_NAME ="P_PARK"                 //공원명
        const val COURSEPARK_DESCRIPTION="P_LIST_CONTENT"   //공원설명
        const val COURSEPARK_LONGITUDE="G_LONGITUDE"        //X좌표(GRS80TM)
        const val COURSEPARK_LATITUDE="G_LATITUDE"          //Y좌표(GRS80TM)
        const val COURSEPARK_VISITROAD ="VISIT_ROAD"        //공원 경로(오시는길)
        const val COURSEPARK_AREA = "P_ZONE"                //공원이 있는 지역
        const val COURSEPARK_URL = "TEMPLATE_URL"           //공원에 대한 바로가기 링크
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentListPrintBinding.inflate(layoutInflater,container,false)
        return binding!!.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecycler()
        initdata()
    }

    //RecyclerView 초기화 하는 부분
    private fun initRecycler(){
        binding!!.recyclerview.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        binding!!.recyclerview.addItemDecoration(DividerItemDecoration(context, LinearLayoutManager.VERTICAL))
        adapter = ParkRecyclerAdapter(parklist)
        binding!!.recyclerview.adapter = adapter
    }

    //XML정보 데이터 초기화 및 출력
    private fun initdata() {
        adapter.data.add(Parkdata("번호", "산책로명", "설명", "x좌표","y좌표","오시는 길", Typeface.BOLD))
        scope.launch {
            val doc = Jsoup.connect(URLPARK).parser(Parser.xmlParser()).get()
            val headlines = doc.select("row")
            for (information in headlines) {
                //공원 정보 출력부
                /* 추출할 xml 정보를 이 부분에서 처리하면 된다. */

                    val num = information.select(COURSEPARK_NUM).text()
                    val name = information.select(COURSEPARK_NAME).text()
                    val description = information.select(COURSEPARK_DESCRIPTION).text()
                    val longitude = information.select(COURSEPARK_LONGITUDE).text()
                    val latitude = information.select(COURSEPARK_LATITUDE).text()
                    val zone = information.select(COURSEPARK_AREA).text()
                    val visitroad = information.select(COURSEPARK_VISITROAD).text()
                    val url = information.select(COURSEPARK_URL).text()
                    adapter.data.add(Parkdata(num, name, description, longitude,latitude,visitroad,Typeface.NORMAL))
            }

            withContext(Dispatchers.Main) {
                adapter.notifyDataSetChanged()
            }
        }
    }

}