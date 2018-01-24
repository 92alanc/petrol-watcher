package com.braincorp.petrolwatcher.fragments

import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.braincorp.petrolwatcher.R
import com.braincorp.petrolwatcher.model.UiMode
import com.braincorp.petrolwatcher.utils.openCamera
import com.braincorp.petrolwatcher.utils.openGallery
import com.braincorp.petrolwatcher.utils.showImagePickerDialogue
import com.google.firebase.auth.FirebaseAuth
import com.squareup.picasso.Picasso
import de.hdodenhof.circleimageview.CircleImageView

class ImagePickerFragment : Fragment(), View.OnClickListener {

    companion object {
        private const val ARG_MODE = "mode"

        fun newInstance(uiMode: UiMode): ImagePickerFragment {
            val instance = ImagePickerFragment()
            val args = Bundle()
            args.putSerializable(ARG_MODE, uiMode)
            instance.arguments = args
            return instance
        }
    }

    private val user = FirebaseAuth.getInstance().currentUser

    private var uiMode = UiMode.VIEW
    private var uri: Uri? = null

    private lateinit var imageViewProfile: CircleImageView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_image_picker, container, false)
        bindViews(view)
        parseArgs()
        prepareUi()
        return view
    }

    override fun onClick(v: View?) {
        if (v?.id == R.id.imageViewProfile) {
            activity!!.showImagePickerDialogue(cameraButtonAction = {
                activity!!.openCamera()
            }, galleryButtonAction = {
                activity!!.openGallery()
            })
        }
    }

    fun getImageUri(): Uri? = uri

    fun setData(uri: Uri?) {
        this.uri = uri
        Picasso.with(context)
                .load(uri)
                .placeholder(R.drawable.ic_profile)
                .into(imageViewProfile)
    }

    private fun bindViews(view: View) {
        imageViewProfile = view.findViewById(R.id.imageViewProfile)
        imageViewProfile.setOnClickListener(this)
    }

    private fun parseArgs() {
        uiMode = arguments?.getSerializable(ARG_MODE) as UiMode
    }

    private fun prepareUi() {
        imageViewProfile.isClickable = (uiMode == UiMode.CREATE || uiMode == UiMode.EDIT)

        if (uiMode == UiMode.EDIT || uiMode == UiMode.VIEW)
            setData(user?.photoUrl)
    }

}