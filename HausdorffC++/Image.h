#include <opencv2/core/core.hpp>
#include <opencv2/highgui/highgui.hpp>
#include <iostream>
#include <cuda_runtime.h>

using namespace cv;
using namespace std;

#pragma once
class Image
{
private:
	cv::Mat image;
	Vec3b auxColor;
	short* grayImg;
	bool* boolImg;

public:
	Image(String path);
	Image(String path, int type);
	//get
	int getHeight();
	int getWidth();
	short getPixel(int x, int y, int band);
	short getSample(int x, int y, int band);
	int getNumBands();
	short* getLinearImage(int band);
	bool* getBooleanLinearImage(int band);
	bool* getBooleanLinearImage(int band, int threshold);
	bool* getPinnedBooleanLinearImage(int band);
	bool* getPinnedBooleanLinearImage(int band, int threshold);
	short getMaxValue(int band);
	//set
	void setPixel(int x, int y, int band, int value);
	void setSample(int x, int y, int band, int value);
	//others
	void writeImage(String path);
	void printImage();
	void printLinearImage(int band);
	void printBooleanLinearImage(int band);
	void displayInWindow(String windowTitle);
	void displayInWindow();
	void dispose();

private:
	int getBand(int band);

};

Image::Image(String path) {
	image = imread(path, IMREAD_COLOR); // Read the file
	if (!image.data)                              // Check for invalid input
	{
		cout << "Could not open or find the image on path: ";
		cout << path << std::endl;
		exit(-1);
	}
}
Image::Image(String path, int type) {
	image = imread(path, type); // Read the file
	if (!image.data)                              // Check for invalid input
	{
		cout << "Could not open or find the image on path: ";
		cout << path << std::endl;
		exit(-1);
	}
}

//get
int Image::getHeight(){
	return image.rows;
}
int Image::getWidth(){
	return image.cols;
}
short Image::getPixel(int x, int y, int band){
	auxColor = image.at<Vec3b>(Point(x, y));
	return auxColor[getBand(band)];
}
short Image::getSample(int x, int y, int band){
	return getPixel(x, y, band);
}
int Image::getBand(int band){
	return (band == 0) ? 2 : (band == 2) ? 0 : 1;
}
int Image::getNumBands(){
	return image.channels();
}
short* Image::getLinearImage(int band){
	grayImg = new short[getHeight()*getWidth()];
	for (int i = 0; i < getHeight(); i++){
		for (int j = 0; j < getWidth(); j++){
			grayImg[i*getWidth() + j] = getPixel(j, i, band);
		}
	}
	return grayImg;
}
bool* Image::getBooleanLinearImage(int band){//if the image is already binary
	return getBooleanLinearImage(band, getMaxValue(band));
}
bool* Image::getBooleanLinearImage(int band, int threshold){
	boolImg = new bool[getHeight()*getWidth()];
	for (int i = 0; i < getHeight(); i++){
		for (int j = 0; j < getWidth(); j++){
			boolImg[i*getWidth() + j] = (getPixel(j, i, band) >= threshold);
		}
	}
	return boolImg;
}
bool* Image::getPinnedBooleanLinearImage(int band){//if the image is already binary
	return getPinnedBooleanLinearImage(band, getMaxValue(band));
}
bool* Image::getPinnedBooleanLinearImage(int band, int threshold){
	bool *img = NULL;
	cudaHostAlloc((void **)&img, getHeight()*getWidth()*sizeof(bool), cudaHostAllocWriteCombined);
	for (int i = 0; i < getHeight(); i++){
		for (int j = 0; j < getWidth(); j++){
			img[i*getWidth() + j] = (getPixel(j, i, band) >= threshold);
		}
	}
	return img;
}
short Image::getMaxValue(int band){
	int max = 0;
	for (int i = 0; i < getHeight(); i++){
		for (int j = 0; j < getWidth(); j++){
			max = (getPixel(j, i, band) > max ? getPixel(j, i, band) : max);
		}
	}
	return max;
}


//set
void Image::setPixel(int x, int y, int band, int value){
	auxColor = image.at<Vec3b>(Point(x, y));
	auxColor[getBand(band)] = value;
}
void Image::setSample(int x, int y, int band, int value){
	setPixel(x, y, band, value);
}


//others
void Image::writeImage(String path){
	imwrite(path, image);
}
void Image::printImage(){
	for (int i = 0; i < getHeight(); i++){
		for (int j = 0; j < getWidth(); j++){
			std::cout << "[";
			for (int b = 0; b < getNumBands(); b++){
				std::cout << getPixel(j, i, b);
				if (b < getNumBands() - 1) std::cout << ",";
			}
			std::cout << "]";
		}
		std::cout << "\n";
	}
}
void Image::printLinearImage(int band){
	getLinearImage(band);
	for (int k = 0; k < getHeight()*getWidth(); k++){
		std::cout << (grayImg[k]) << " ";
	}
	std::cout << "\n";
}
void Image::printBooleanLinearImage(int band){
	getBooleanLinearImage(band);
	for (int k = 0; k < getHeight()*getWidth(); k++){
		std::cout << (boolImg[k]) << " ";
	}
	std::cout << "\n";
}
void Image::displayInWindow(){
	displayInWindow("Image");
}
void Image::displayInWindow(String windowTitle){
	namedWindow(windowTitle, WINDOW_AUTOSIZE); // Create a window for display.
	imshow(windowTitle, image); // Show our image inside it.
}
void Image::dispose(){
	delete[] grayImg;
}


