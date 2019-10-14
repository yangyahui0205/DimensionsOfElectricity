package com.baway.dimensionsofelectricity.ui.activity;

import android.support.v4.app.FragmentTransaction;
import android.widget.FrameLayout;
import android.widget.RadioGroup;

import com.baway.dimensionsofelectricity.R;
import com.baway.dimensionsofelectricity.di.contract.IHomeContract;
import com.baway.dimensionsofelectricity.di.presenter.HomePresenter;
import com.baway.dimensionsofelectricity.ui.fragment.CircleFragment;
import com.baway.dimensionsofelectricity.ui.fragment.ListFragment;
import com.baway.dimensionsofelectricity.ui.fragment.HomePageFragment;
import com.baway.dimensionsofelectricity.ui.fragment.MineFragment;
import com.baway.dimensionsofelectricity.ui.fragment.ShoppingCartFragment;

import butterknife.BindView;

public class HomeActivity extends BaseActivity<IHomeContract.IHomeView, HomePresenter<IHomeContract.IHomeView>> implements IHomeContract.IHomeView {


    @BindView(R.id.fl_layout)
    FrameLayout flLayout;
    @BindView(R.id.rg_bottom_bar)
    RadioGroup rgBottomBar;
    private FragmentTransaction fragmentTransaction;
    private HomePageFragment homePageFragment;
    private CircleFragment circleFragment;
    private ShoppingCartFragment shoppingCartFragment;
    private ListFragment listFragment;
    private MineFragment mineFragment;

    @Override
    public void showHomeData() {

    }

    @Override
    protected HomePresenter<IHomeContract.IHomeView> createPresenter() {
        return new HomePresenter<>();
    }

    @Override
    protected void initData() {
        fragmentTransaction = getSupportFragmentManager().beginTransaction();

        homePageFragment = new HomePageFragment();
        circleFragment = new CircleFragment();
        shoppingCartFragment = new ShoppingCartFragment();
        listFragment = new ListFragment();
        mineFragment = new MineFragment();

        fragmentTransaction
                .add(R.id.fl_layout, homePageFragment).show(homePageFragment)
                .add(R.id.fl_layout, circleFragment).hide(circleFragment)
                .add(R.id.fl_layout, shoppingCartFragment).hide(shoppingCartFragment)
                .add(R.id.fl_layout, listFragment).hide(listFragment)
                .add(R.id.fl_layout, mineFragment).hide(mineFragment)
                .commit();

        rgBottomBar.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                switch (checkedId) {
                    case R.id.rb_home:
                        transaction.show(homePageFragment).hide(circleFragment).hide(shoppingCartFragment).hide(listFragment).hide(mineFragment);
                        break;
                    case R.id.rb_circle:
                        transaction.show(circleFragment).hide(homePageFragment).hide(shoppingCartFragment).hide(listFragment).hide(mineFragment);
                        break;
                    case R.id.rb_shoppingcart:
                        transaction.show(shoppingCartFragment).hide(circleFragment).hide(homePageFragment).hide(listFragment).hide(mineFragment);
                        break;
                    case R.id.rb_list:
                        transaction.show(listFragment).hide(circleFragment).hide(shoppingCartFragment).hide(homePageFragment).hide(mineFragment);
                        break;
                    case R.id.rb_my:
                        transaction.show(mineFragment).hide(circleFragment).hide(shoppingCartFragment).hide(listFragment).hide(homePageFragment);
                        break;

                }
                transaction.commit();
            }
        });
    }

    @Override
    protected int bindLayout() {
        return R.layout.activity_home;
    }

}
