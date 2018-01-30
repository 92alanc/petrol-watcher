package com.braincorp.petrolwatcher.fragments

import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.ImageButton
import com.braincorp.petrolwatcher.R
import com.braincorp.petrolwatcher.model.UiMode
import com.braincorp.petrolwatcher.utils.*
import com.google.firebase.auth.FirebaseAuth
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

    private var bitmap: Bitmap? = null
    private var uiMode = UiMode.VIEW
    private var uri: Uri? = null

    private var rotation = 0f

    private lateinit var imageViewProfile: CircleImageView
    private lateinit var buttonRotateClockwise: ImageButton

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_image_picker, container, false)
        bindViews(view)
        parseArgs()
        prepareUi()
        return view
    }

    override fun onClick(v: View?) {
        when {
            v?.id == R.id.imageViewProfile -> activity!!.showImagePickerDialogue(cameraButtonAction = {
                activity!!.openCamera()
            }, galleryButtonAction = {
                activity!!.openGallery()
            })

            v?.id == R.id.buttonRotateClockwise -> {
                if (rotation == 360f) rotation = 0f
                else rotation += 90f
                bitmap = rotateBitmap(bitmap!!, rotation)
                context?.fillImageView(imageViewProfile, uri, placeholder = R.drawable.ic_profile,
                        rotation = rotation)
            }
        }
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
        context?.fillImageView(imageViewProfile, uri, placeholder = R.drawable.ic_profile)
    }

    private fun bindViews(view: View) {
        imageViewProfile = view.findViewById(R.id.imageViewProfile)
        imageViewProfile.setOnClickListener(this)

        buttonRotateClockwise = view.findViewById(R.id.buttonRotateClockwise)
        buttonRotateClockwise.setOnClickListener(this)
    }

    private fun parseArgs() {
        uiMode = arguments?.getSerializable(ARG_MODE) as UiMode
    }

    private fun prepareUi() {
        imageViewProfile.isClickable = (uiMode == UiMode.CREATE || uiMode == UiMode.EDIT)

        buttonRotateClockwise.visibility =
                if (uiMode == UiMode.CREATE || uiMode == UiMode.EDIT) VISIBLE
                else GONE

        if (uiMode == UiMode.EDIT || uiMode == UiMode.VIEW) {
            bitmap = (imageViewProfile.drawable as BitmapDrawable).bitmap
            setImageUri(FirebaseAuth.getInstance().currentUser?.photoUrl)
        }
    }

}