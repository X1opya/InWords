package Main.SearchUI;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListView;

import com.x1opya.inwords.R;

import Main.WordsListAdapter;
import Main.SearchUI.Data.Word;
import Main.SearchUI.Data.WordsManager;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link SearchFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link SearchFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SearchFragment extends Fragment {

    WordsListAdapter adapter;
    ListView listView;
    List<Word> list;

    public SearchFragment() {
        // Required empty public constructor

    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment SearchFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SearchFragment newInstance() {
        SearchFragment fragment = new SearchFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);
        initSerchAndList(view);
        // Inflate the layout for this fragment
        return view;
    }

    private void initSerchAndList(View view){
        EditText sv = view.findViewById(R.id.search_view);
        final WordsManager wordsManager = new WordsManager(getActivity().getBaseContext());
        listView = view.findViewById(R.id.list);
        list = new ArrayList<>();
        adapter = new WordsListAdapter(getActivity().getBaseContext(), R.layout.search_item,list);
        listView.setAdapter(adapter);

        sv.addTextChangedListener(new TextWatcher() {
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
                if(!charSequence.toString().isEmpty() && Character.getNumericValue(charSequence.toString().charAt(0))>0)
                    isEng = true;
                else//русских
                    isEng=false;

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
