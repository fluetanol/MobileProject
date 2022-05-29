package com.example.mobileproject

import android.graphics.Typeface
import android.os.Build
import android.os.Bundle
import android.text.Html
import android.text.Html.FROM_HTML_MODE_LEGACY
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mobileproject.RecyclerAdapter.ForestRecyclerAdapter
import com.example.mobileproject.databinding.FragmentListPrintBinding
import com.example.mobileproject.dataclass.Forestdata
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.jsoup.Jsoup
import org.jsoup.parser.Parser
import java.io.Serializable

/*ListPrint(인자: 어떤 걸 출력할건지에 대한 플래그값)
메인 액티비티 컴패니언 객체안에
const val FLAG_PRINT_PARK = 0
const val FLAG_PRINT_FOREST = 1
의 내용을 참고하면 됨*/

class ForestListPrintFragment() : Fragment(), Serializable{
    var binding:FragmentListPrintBinding?= null
    val scope = CoroutineScope(Dispatchers.IO)

    lateinit var adapter: ForestRecyclerAdapter
    var mountainlist = arrayListOf<Forestdata>()

    val URLFOREST = "http://openapi.seoul.go.kr:8088/4646617a476d696b313138566b6e727a/xml/SeoulGilWalkCourse/1/985"


    companion object{
        const val COURSE_NAME ="COURSE_CATEGORY_NM"         //산책코스명
        const val COURSE_CONTENT ="CONTENT"                 //산책코스 설명
        const val COURSE_X ="X"                             //산책코스 X좌표
        const val COURSE_Y ="Y"                             //산책코스 Y좌표
        const val COURSE_DISTANCE ="DISTANCE"               //산책거리
        const val COURSE_TIME = "LEAD_TIME"                 //산책소요시간
        const val COURSE_TRAFFIC ="TRAFFIC_INFO"            //교통편 (오시는길)
        const val COURSE_SUBWAY ="RELATE_SUBWAY"            //근처 지하철
    }


    //프레그먼트 binding초기화 작업이여서 별 볼일 없음
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentListPrintBinding.inflate(layoutInflater,container,false)
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecycler()
        initdata()
    }

    //RecyclerView 초기화 하는 부분(레이아웃 매니저를 바꿀 게 아니면 건들 필요 없음)
    private fun initRecycler(){
        binding!!.recyclerview.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        binding!!.recyclerview.addItemDecoration(DividerItemDecoration(context, LinearLayoutManager.VERTICAL))
        adapter = ForestRecyclerAdapter(mountainlist)
        binding!!.recyclerview.adapter = adapter
    }

    //XML정보 데이터 초기화 및 출력
    private fun initdata() {
            adapter.data.add(Forestdata("번호", "산책로명", "설명", "x좌표","y좌표","오시는 길", "근처 지하철","산책 소요시간", Typeface.BOLD))
            scope.launch {
                val doc = Jsoup.connect(URLFOREST).parser(Parser.xmlParser()).get()
                val headlines = doc.select("row")

                var i =1;
                for (information in headlines) {

                    /* 추출할 xml 정보를 이 부분에서 처리하면 된다. 알아서 원하는 부분만 걸러낼 것 */
                    val num = i++.toString()

                    val name = information.select(COURSE_NAME).text()
                    val X = information.select(COURSE_X).text()
                    val Y = information.select(COURSE_Y).text()
                    val traffic = information.select(COURSE_TRAFFIC).text()
                    val subway = information.select(COURSE_SUBWAY).text()
                    val distance = information.select(COURSE_DISTANCE).text()
                    val time = information.select(COURSE_TIME).text()
                    var description = information.select(COURSE_CONTENT).text()
                        /*만약 추출한 xml정보에 html태그가 껴있는 경우 이 구문을 활용하자.*/
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
                        description= Html.fromHtml(description,FROM_HTML_MODE_LEGACY).toString()
                    else
                        description= Html.fromHtml(description).toString() //fromHtml(html 태그만 제거할 문자열)

                    adapter.data.add(Forestdata(num, name, description,X,Y,traffic, subway,time,Typeface.NORMAL))

                }
                withContext(Dispatchers.Main) {
                    adapter.notifyDataSetChanged()
                }
            }
        }

}
