package com.braincorp.petrolwatcher.fragments

import android.app.Fragment
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import com.braincorp.petrolwatcher.R
import com.braincorp.petrolwatcher.model.AdaptableUi
import com.braincorp.petrolwatcher.utils.fillImageView
import com.braincorp.petrolwatcher.utils.openCamera
import com.braincorp.petrolwatcher.utils.openGallery
import com.braincorp.petrolwatcher.utils.showImagePickerDialogue
import com.google.firebase.auth.FirebaseAuth
import de.hdodenhof.circleimageview.CircleImageView

class ImagePickerFragment : Fragment(), View.OnClickListener, AdaptableUi {

    companion object {
        private const val ARG_MODE = "mode"

        fun newInstance(uiMode: AdaptableUi.Mode): ImagePickerFragment {
            val instance = ImagePickerFragment()
            val args = Bundle()
            args.putSerializable(ARG_MODE, uiMode)
            instance.arguments = args
            return instance
        }
    }

    private var bitmap: Bitmap? = null
    private var uiMode = AdaptableUi.Mode.VIEW
    private var uri: Uri? = null

    private lateinit var imageViewProfile: CircleImageView
    private lateinit var progressBar: ProgressBar

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_image_picker, container, false)
        bindViews(view)
        parseArgs()
        prepareUi()
        return view
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.imageViewProfile -> activity!!.showImagePickerDialogue(cameraButtonAction = {
                activity!!.openCamera()
            }, galleryButtonAction = {
                activity!!.openGallery()
            })
        }
    }

    override fun prepareInitialMode() {
        prepareViewMode()
    }

    override fun prepareCreateMode() {
        imageViewProfile.isClickable = true
    }

    override fun prepareEditMode() {
        imageViewProfile.isClickable = true
        bitmap = (imageViewProfile.drawable as BitmapDrawable).bitmap
        setImageUri(FirebaseAuth.getInstance().currentUser?.photoUrl)
    }

    override fun prepareViewMode() {
        imageViewProfile.isClickable = false
        bitmap = (imageViewProfile.drawable as BitmapDrawable).bitmap
        setImageUri(FirebaseAuth.getInstance().currentUser?.photoUrl)
    }

    fun getImageBitmap(): Bitmap {
        return (imageViewProfile.drawable as BitmapDrawable).bitmap
    }

    fun setImageBitmap(bitmap: Bitmap?) {
        this.bitmap = bitmap
        imageViewProfile.setImageBitmap(bitmap)
    }

    fun setImageUri(uri: Uri?) {
        this.uri = uri
        activity.fillImageView(uri, imageViewProfile, progressBar = progressBar)
    }

    private fun bindViews(view: View) {
        imageViewProfile = view.findViewById(R.id.imageViewProfile)
        imageViewProfile.setOnClickListener(this)

        progressBar = view.findViewById(R.id.progressBar)
    }

    private fun parseArgs() {
        uiMode = arguments?.getSerializable(ARG_MODE) as AdaptableUi.Mode
    }

    private fun prepareUi() {
        when (uiMode) {
            AdaptableUi.Mode.INITIAL -> prepareInitialMode()
            AdaptableUi.Mode.CREATE -> prepareCreateMode()
            AdaptableUi.Mode.EDIT -> prepareEditMode()
            AdaptableUi.Mode.VIEW -> prepareViewMode()
        }
    }

}