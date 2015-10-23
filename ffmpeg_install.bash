#!/bin/bash

function ffmpeg_install
{
	local CURDIR=`pwd`
	git clone git://source.ffmpeg.org/ffmpeg.git /tmp/ffmpeg
	cd /tmp/ffmpeg
	./configure\
		--logfile=ffmpeg.log\
	       	--prefix="${HOME}/ffmpeg"\
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
		--enable-libopenh264
	make
	make install
	cd "$CURDIR"
}

ffmpeg_install
