package com.example.admin.ebuy.util;

import android.os.Bundle;

import com.example.admin.ebuy.base.BaseFragment;
import com.example.admin.ebuy.home.HomeFragment;
import com.example.admin.ebuy.home.ProductDetailFragment;
import com.example.admin.ebuy.home.ShopDetailFragment;
import com.example.admin.ebuy.home.TypeFragment;
import com.example.admin.ebuy.home.TypeProductFragment;
import com.example.admin.ebuy.list.ChooseListProductFragment;
import com.example.admin.ebuy.list.ChooseListTypeFragment;
import com.example.admin.ebuy.list.ChooseListTypeProductFragment;
import com.example.admin.ebuy.list.ListProductFragment;
import com.example.admin.ebuy.location.MapsFragment;
import com.example.admin.ebuy.shopping.ShoppingFragment;
import com.example.admin.ebuy.user.AddProductFragment;
import com.example.admin.ebuy.user.EditProfileFragment;
import com.example.admin.ebuy.user.LoginFragment;
import com.example.admin.ebuy.user.RegisterFragment;
import com.example.admin.ebuy.user.UserFragment;


/**
 * Created by kelvin on 07/07/18.
 */

public class FragmentProvider {
    /**
     * CREATE FRAGMENT
     *
     * @param fragmentClassName
     * @param data
     * @return
     */
    public static BaseFragment getFragmentNewInstance(String fragmentClassName, Bundle data) {
        if (data == null)
            data = new Bundle();

        BaseFragment fragment = null;
        switch (fragmentClassName) {

            case HomeFragment.TAG:
                return new HomeFragment();
            case ListProductFragment.TAG:
                return new ListProductFragment();
            case ShoppingFragment.TAG:
                return new ShoppingFragment();
            case UserFragment.TAG:
                return new UserFragment();
            case ProductDetailFragment.TAG:
                return new ProductDetailFragment();
            case TypeFragment.TAG:
                return new TypeFragment();
            case TypeProductFragment.TAG:
                return new TypeProductFragment();
            case LoginFragment.TAG:
                return new LoginFragment();
            case ShopDetailFragment.TAG:
                return new ShopDetailFragment();
            case RegisterFragment.TAG:
                return new RegisterFragment();
            case MapsFragment.TAG:
                return new MapsFragment();
            case EditProfileFragment.TAG:
                return new EditProfileFragment();
            case ChooseListProductFragment.TAG:
                return new ChooseListProductFragment();
            case ChooseListTypeFragment.TAG:
                return new ChooseListTypeFragment();
            case ChooseListTypeProductFragment.TAG:
                return new ChooseListTypeProductFragment();
            case AddProductFragment.TAG:
                return new AddProductFragment();
        }

        if (fragment != null)
            fragment.setArguments(data);

        return fragment;
    }
}
