package yayang.setiyawan.skin_diase.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import yayang.setiyawan.skin_diase.R
import yayang.setiyawan.skin_diase.databinding.FragmentAkunBinding

class AkunFragment : Fragment() {
    private lateinit var binding:FragmentAkunBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentAkunBinding.inflate(layoutInflater,container,false)
        return binding.root
    }
    }