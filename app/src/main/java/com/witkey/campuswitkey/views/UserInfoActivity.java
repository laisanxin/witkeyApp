package com.witkey.campuswitkey.views;

import android.Manifest;
import android.app.Activity;
import android.content.ContentUris;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaDataSource;
import android.net.Uri;
import android.os.Build;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.witkey.campuswitkey.R;
import com.witkey.campuswitkey.adapter.ListAdapter;
import com.witkey.campuswitkey.adapter.ListAdapter2;
import com.witkey.campuswitkey.adapter.ListItem;
import com.witkey.campuswitkey.contract.IActivityLifeCycle;
import com.witkey.campuswitkey.contract.UserInfoContract;
import com.witkey.campuswitkey.model.entity.User;
import com.witkey.campuswitkey.presenter.UserInfoActivityPresenter;
import com.witkey.campuswitkey.utils.Url;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class UserInfoActivity extends AppCompatActivity implements UserInfoContract.IUserInfoActivity {

    @BindView(R.id.userinfo_toolbar)
    Toolbar toolbar;
    @BindView(R.id.user_head_view)
    ImageView user_head_view;
    @BindView(R.id.change_head_tv)
    TextView change_head_tv;
    @BindView(R.id.userinfo_list_view)
    ListView listView;
    @BindView(R.id.user_update_btn)
    Button user_update_btn;
    private ArrayList<ListItem> list = new ArrayList<>();
    private UserInfoContract.IUserInfoActivityPresenter presenter;
    private ListAdapter2 adapter2;
    private User user = new User();
    private String imgPath;
    public static final int TAKE_HEAD_PHOTO = 10;
    public static final int CHOOSE_HEAD_PHOTO = 11;
    private Uri headUri;
    private int flag_head = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        ActionBar actionbar = getSupportActionBar();
        if(actionbar != null){
            actionbar.setTitle(null);
            actionbar.setDisplayHomeAsUpEnabled(true);
            actionbar.setHomeAsUpIndicator(R.drawable.ic_action_back);
        }
        presenter = initPresenter();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position){
                    case 0:
                        Intent intent = new Intent(UserInfoActivity.this, UserInfoEditActivity.class);
                        intent.putExtra("id",0);
                        TextView wanring  = (TextView) view.findViewById(R.id.item_warning_tv);
                        intent.putExtra("content",wanring.getText().toString());
                        startActivityForResult(intent, 0);
                        break;
                    case 1:
                        intent = new Intent(UserInfoActivity.this, UserInfoEditActivity.class);
                        intent.putExtra("id",1);
                        wanring  = (TextView) view.findViewById(R.id.item_warning_tv);
                        intent.putExtra("content",wanring.getText().toString());
                        startActivityForResult(intent, 1);
                        break;
                    case 2:
                        intent = new Intent(UserInfoActivity.this, UserInfoEditActivity.class);
                        intent.putExtra("id",2);
                        wanring  = (TextView) view.findViewById(R.id.item_warning_tv);
                        intent.putExtra("content",wanring.getText().toString());
                        startActivityForResult(intent, 2);
                        break;
                    case 3:
                        intent = new Intent(UserInfoActivity.this, UserInfoEditActivity.class);
                        intent.putExtra("id",3);
                        wanring  = (TextView) view.findViewById(R.id.item_warning_tv);
                        intent.putExtra("content",wanring.getText().toString());
                        startActivityForResult(intent, 3);
                        break;
                    default:
                        break;
                }
            }
        });
        presenter.attach(this);
        presenter.loadUserInfo();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                break;
            default:
        }
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode==RESULT_OK){
            switch (requestCode){
                case 0:
                    user.setUser_name(data.getStringExtra("content"));
                    list.get(0).setItemWarning(user.getUser_name());
                    adapter2 = new ListAdapter2(this, list);
                    listView.setAdapter(adapter2);
                    break;
                case 1:
                    user.setUser_sex(data.getStringExtra("content"));
                    list.get(1).setItemWarning(user.getUser_sex());
                    adapter2 = new ListAdapter2(this, list);
                    listView.setAdapter(adapter2);
                    break;
                case 2:
                    user.setUser_major(data.getStringExtra("content"));
                    Log.i("major",data.getStringExtra("content"));
                    list.get(2).setItemWarning(user.getUser_major());
                    adapter2 = new ListAdapter2(this, list);
                    listView.setAdapter(adapter2);
                    break;
                case 3:
                    break;
                case TAKE_HEAD_PHOTO:
                    String filePath = headUri.toString();
                    filePath = filePath.substring(7);
                    user.setUser_head(filePath);
                    Log.i("takephoto",filePath);
                    Glide.with(this).load(filePath).into(user_head_view);
                    flag_head = 1;
                    break;
                case CHOOSE_HEAD_PHOTO:
                    handleImage(data);
                    break;
                default:
                    break;
            }
        }
    }

    private void handleImage(Intent data) {
        String imagePath = null;
        Uri uri = data.getData();
        if(DocumentsContract.isDocumentUri(this, uri)){
            //如果是document类型的Uri, 则通过documnet id 处理
            String docId = DocumentsContract.getDocumentId(uri);
            if("com.android.providers.media.documents".equals(uri.getAuthority())){
                String id = docId.split(":")[1];
                String selection = MediaStore.Images.Media._ID + "=" + id;
                imagePath = getImagePath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, selection);

            }else if("com.android.providers.downloads.documents".equals(uri.getAuthority())){
                Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"),
                        Long.valueOf(docId));
                imagePath = getImagePath(contentUri, null);

            }
        }else  if("content".equalsIgnoreCase(uri.getScheme())){
            //如果是content类型的Uri, 则使用普通方式处理
            imagePath = getImagePath(uri, null);

        }else if("file".equalsIgnoreCase(uri.getScheme())){
            //如果是file类型的Uri，直接获取图片路径即可
            imagePath = uri.getPath();
        }
        displayImage(imagePath);
    }

    private void displayImage(String imagePath) {
        if(imagePath != null){
            Glide.with(this).load(imagePath).into(user_head_view);
            user.setUser_head(imagePath);
            Log.i("choosephoto",imagePath);
            flag_head = 1;
        }else {
            Toast.makeText(this, "无法获取图片", Toast.LENGTH_SHORT).show();;
        }
    }

    private String getImagePath(Uri uri, String selection) {
        String path = null;
        Cursor cursor = getContentResolver().query(uri, null, selection,null, null);
        if(cursor != null){
            if(cursor.moveToFirst()){
                path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
            }
            cursor.close();
        }
        return path;
    }

    @OnClick(R.id.change_head_tv)
    public void changeHead(){
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle("请选择");
        String items[] = {"相机","相册"};
        dialog.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(which==0){
                    int i = (int)(Math.random()*100000);
                    File headImage = new File(getExternalCacheDir(),"img"+i+".jpg");
                    try{
                        if(headImage.exists()){
                            headImage.delete();
                        }
                        headImage.createNewFile();
                    }catch (IOException e){
                        e.printStackTrace();
                    }
                    if(Build.VERSION.SDK_INT >= 24){
                        headUri = FileProvider.getUriForFile(UserInfoActivity.this, "com.witkey.campuswitkey.fileprovider", headImage);
                    }else {
                        headUri = Uri.fromFile(headImage);
                    }
                    Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, headUri);
                    startActivityForResult(intent, TAKE_HEAD_PHOTO);
                }else if(which==1){
                    if(ContextCompat.checkSelfPermission(UserInfoActivity.this,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.
                            PERMISSION_GRANTED){
                        ActivityCompat.requestPermissions(UserInfoActivity.this, new String[]{
                                Manifest.permission.WRITE_EXTERNAL_STORAGE},1);
                    }else {
                        openAlbum();
                    }
                }

            }
        }).create().show();
    }

    private void openAlbum() {
        Intent intent = new Intent("android.intent.action.GET_CONTENT");
        intent.setType("image/*");
        startActivityForResult(intent, CHOOSE_HEAD_PHOTO);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case 1:
                if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    openAlbum();
                }else {
                    Toast.makeText(this, "你拒绝了访问相册的权限", Toast.LENGTH_SHORT).show();
                }
                break;
            default:
        }
    }

    @OnClick(R.id.user_update_btn)
    public void userUpdate() {
        if(flag_head==1){
            imgPath = user.getUser_head();
        }
        presenter.updateUserInfo(user);

    }

    @Override
    public void showResult(String reslut) {
        Toast.makeText(this, reslut, Toast.LENGTH_SHORT).show();

    }

    @Override
    public void showLoading() {

    }

    @Override
    public void dismissLoading() {

    }

    @Override
    public void showUserInfo(User user2) {
        user = user2;
        ListItem item = new ListItem("昵称",user.getUser_name(),R.drawable.ic_chevron_right);
        list.add(item);
        item = new ListItem("性别",user.getUser_sex(),R.drawable.ic_chevron_right);
        list.add(item);
        item = new ListItem("专业",user.getUser_major(),R.drawable.ic_chevron_right);
        list.add(item);
        item = new ListItem("邮箱",user.getUser_email(),R.drawable.ic_chevron_right);
        list.add(item);
        adapter2 = new ListAdapter2(this,list);
        listView.setAdapter(adapter2);
        Glide.with(this)
                .load(Url.Base_URL_HEAD+"?path="+user.getUser_head())
                .error(R.mipmap.ic_launcher)
                .into(user_head_view);
        imgPath = Url.Base_URL_HEAD+"?path="+user.getUser_head();

    }
    @OnClick(R.id.change_head_tv)
    public void checkHead(){

    }
    @Override
    public void showUpdateResult(String result) {
        Toast.makeText(UserInfoActivity.this, result, Toast.LENGTH_SHORT).show();


    }

    @Override
    public void backActivity() {
        Intent intent = new Intent();
        intent.putExtra("head",imgPath);
        intent.putExtra("user_name",user.getUser_name());
        setResult(RESULT_OK, intent);
        finish();
    }

    @Override
    public UserInfoContract.IUserInfoActivityPresenter initPresenter() {
        return new UserInfoActivityPresenter(this);

    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.dettach();
    }
}
