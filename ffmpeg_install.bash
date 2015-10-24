#!/bin/bash

function ffmpeg_install
{
	local CURDIR=`pwd`
	git clone git://source.ffmpeg.org/ffmpeg.git /tmp/ffmpeg
	cd /tmp/ffmpeg
	./configure\
		--arch=x86_32\
		--cc='gcc -m32'\
		--logfile=ffmpeg.log\
	       	--prefix="${HOME}/ffmpeg"\
		--extra-version="Built-by-Tim"\
	       	--disable-ffplay\
	       	--disable-ffprobe\
		--disable-ffserver\
	       	--disable-doc\
		--disable-htmlpages\
		--disable-manpages\
		--disable-podpages\
		--disable-txtpages\
	       	--enable-gpl\
	       	--enable-nonfree\
	       	--enable-libx264\
		--enable-libfreetype\
		--enable-libass\
		--enable-libmp3lame\
		--enable-libopenjpeg\
		--enable-libschroedinger\
		--enable-libtheora\
		--enable-libvorbis\
		--enable-libvpx\
		--enable-zlib\
		--enable-libxvid\
		--enable-bzlib\
		--enable-libfdk-aac
	if [ $? -ne 0 ]; then
		echo "Configuration failed. Exiting..."
		return 1
	fi
	make
	make install
	cd "$CURDIR"
	return 0
}

ffmpeg_install
