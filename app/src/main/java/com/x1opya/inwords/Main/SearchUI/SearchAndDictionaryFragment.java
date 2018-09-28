package com.x1opya.inwords.Main.SearchUI;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;

import com.x1opya.inwords.R;

import com.x1opya.inwords.Main.Data.Word;
import com.x1opya.inwords.Main.Data.WordsManager;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link SearchAndDictionaryFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link SearchAndDictionaryFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SearchAndDictionaryFragment extends Fragment {

    private WordsListAdapter lvAdapter;
    private WordsListAdapter rvAdapter;
    private RecyclerView recyclerView;
    private ScrollView svList;
    private ScrollView svRecycler;
    private List<Word> list;
    private EditText etSearch;
    private Button animView;
    private View view;
    private LinearLayout listContainer;
    private Animation animation;

    private boolean isSearch = true;

    public SearchAndDictionaryFragment() {
        // Required empty public constructor

    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment SearchAndDictionaryFragment.
     */
    // TODO: Rename and change types and number of parameters
    public SearchAndDictionaryFragment newInstance(boolean isSear) {

        isSearch = isSear;
        Log.println(Log.ASSERT,"","Получатель = "+isSearch + " отправитель = " + isSear);
        //SearchAndDictionaryFragment fragment = new SearchAndDictionaryFragment();
        Bundle args = new Bundle();
        this.setArguments(args);

        return this;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //etSearch.setAnimation(animation);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_search_and_dict, container, false);
        listContainer = view.findViewById(R.id.liner_container);
        etSearch = view.findViewById(R.id.search_view);
        animView = view.findViewById(R.id.anim_view);
        Log.println(Log.ASSERT,this.toString(),"Мод поиска = "+isSearch);
        if(isSearch){
            animation = AnimationUtils.loadAnimation(getContext(),R.anim.serchview);
            ListView listView = view.findViewById(R.id.list);
            svList = view.findViewById(R.id.svList);
            svList.setVisibility(View.VISIBLE);
            searchAnim();
            initListView(listView);
            initSerch();
        }
        else{
            etSearch.setVisibility(View.VISIBLE);
            svRecycler = view.findViewById(R.id.svRecycler);
            svRecycler.setVisibility(View.VISIBLE);
            animView.setVisibility(View.GONE);
            listContainer.setVisibility(View.VISIBLE);
            recyclerView = view.findViewById(R.id.recycler_view);
        }
        // Inflate the layout for this fragment
        return view;
    }

    private void initListView(ListView listView) {
        list = new ArrayList<>();
        WordsListAdapter adapter = new WordsListAdapter(getActivity().getBaseContext(), R.layout.search_item,list);
        listView.setAdapter(adapter);
    }

    private void searchAnim(){
        animView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                animView.startAnimation(animation);
            }
        });
        //etSearch.setEnabled(true);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                animView.setVisibility(View.GONE);
                etSearch.setVisibility(View.VISIBLE);
                etSearch.requestFocus();
                listContainer.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }

    private void initSerch(){
        final WordsManager wordsManager = new WordsManager(getActivity().getBaseContext());
        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int befor, int count) {
                // UTF-16
                //Индекс в кодировке A(английская) = 10, Z = 35
                //Русские символы все = -1
                //Если ввод начался с английской буквы - обращайемся к поиску английских слов
                adapter.clear();
                boolean isEng = false;
                isEng = isEngInput(charSequence.toString());
                List<Word> newList = wordsManager.getWordsToSearch(charSequence.toString(),isEng);//получаем список имеющихся слов с базы
                if(newList!=null) {
                    adapter.addAll(newList);
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    private boolean isEngInput(String input){
        if(input.isEmpty() && Character.getNumericValue(input.charAt(0))>0)
            return  true;
        else//русских
            return false;
    }

    @Override
    public void onStop() {
        super.onStop();
        etSearch.clearFocus();
        etSearch.setText("");
    }



    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
