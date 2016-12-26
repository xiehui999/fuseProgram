package com.example.xh.ui;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.greendao.DaoMaster;
import com.example.greendao.DaoSession;
import com.example.greendao.Person;
import com.example.greendao.PersonDao;
import com.example.xh.R;

import java.util.List;

import de.greenrobot.dao.query.QueryBuilder;

/**
 * Created by xiehui on 2016/12/23.
 */
public class GreenDaoFragment extends Fragment implements View.OnClickListener {

    private TextView textView;
    private Button add,delete,update,query;
    private EditText et_id,et_name,et_age;
    private DaoMaster.DevOpenHelper mHelper;
    private SQLiteDatabase db;
    private DaoMaster mDaoMaster;
    private DaoSession mDaoSession;
    private PersonDao mPersonDao;
    private Cursor cursor;
    private long id;
    private int age;
    private String name;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        textView = (TextView) view.findViewById(R.id.text);
        add = (Button) view.findViewById(R.id.add);
        query = (Button) view.findViewById(R.id.query);
        delete = (Button) view.findViewById(R.id.delete);
        update = (Button) view.findViewById(R.id.update);
        et_age= (EditText) view.findViewById(R.id.et_age);
        et_id= (EditText) view.findViewById(R.id.et_id);
        et_name= (EditText) view.findViewById(R.id.et_name);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.greendao, null);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        add.setOnClickListener(this);
        query.setOnClickListener(this);
        update.setOnClickListener(this);
        delete.setOnClickListener(this);
        query();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mHelper=new DaoMaster.DevOpenHelper(getActivity(),"dbtest",null);
        db=mHelper.getWritableDatabase();
        mDaoMaster= new DaoMaster(db);
        mDaoSession=mDaoMaster.newSession();
        mPersonDao= mDaoSession.getPersonDao();
        //cursor=db.query(mPersonDao.getTablename(),mPersonDao.getAllColumns(),null,null,null,null,null);

    }

    @Override
    public void onClick(View v) {
        name=et_name.getText().toString().trim();
        try {
            age=Integer.parseInt(et_age.getText().toString().trim());
            id=Long.parseLong(et_id.getText().toString().trim());

        }catch (Exception e){
            age=25;
            id=1;
            e.printStackTrace();
        }
        switch (v.getId()) {
            case R.id.add:
                add();
                break;
            case R.id.update:
                update();
                break;
            case R.id.delete:
                delete();
                break;
            case R.id.query:
                query();
                break;

        }
        query();
    }

    private void add() {
        Person person=new Person(null,name,age);
        long l= mPersonDao.insert(person);
        System.out.println("插入返回值:"+l);
    }
    private void query() {
        textView.setText("");
        //where条件判断,orderAsc排序,limit查询条数,list() 查询结果为一个集合,and
        //unique()或uniqueOrThrow()，返回单个结果，如果没有满足条件的结果，前者返回null， 后者抛出异常
        QueryBuilder<Person> queryBuilder=mPersonDao.queryBuilder()/*.where(PersonDao.Properties.Name.eq(name))*/;
        List<Person> persons = queryBuilder.list();
        //List<Person> persons1 = mPersonDao.loadAll();//也可以查询所有数据
        for (Person person:persons){
            textView.append(person.toString()+"\n");
        }

    }

    private void delete() {
        mPersonDao.deleteByKey(id);
       // mPersonDao.deleteAll();//删除所有
       // mPersonDao.delete(new Person(id,name,age));//删除指定值项
    }

    private void update() {
        mPersonDao.update(new Person(id,name,age));
    }

}
