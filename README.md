# hci

Two GUI implementations for group HCI

## Aesthetic Interface ##

An interface designed by Darcy Vreeken and implemented by Tim Peskett. This
interface emphasises aesthetics. It is not intended to be an actual usable
ffmpeg graphical user interface, but more as a high-level mockup.

![Initial Design](ffmpeg-design.png)

The initial design as proposed in our high-fidelity mockups is pictured above.
This design was created by Darcy Vreeken, and was designed to be an
aesthetically appealing interface.

I worked on implementing this interface using JavaFX. The product of this
implementation is shown below. The interface is functional, as I wrote a class
to interface with ffmpeg. The final product was kept very close to the
original design.

![Main Screen](ffmpeg-prog-main.png)
![Other Theme](ffmpeg-prog-white.png)

Above is the main screen of the application, shown in two different themes.
The themes were easy to implement using JavaFX css. The three banners in the
middle are buttons that can be used to navigate to the appropriate parts of
the application. The three buttons correspond to the three screens shown
below.

![Video Screen](ffmpeg-prog-video.png)

This is the screen used for converting videos. It has a preview window to show
the user the media that they are about to convert. This uses JavaFX's in-built
MediaPlayer class.

![Audio Screen](ffmpeg-prog-audio.png)

This is the screen used for converting audio. It has a preview audio player
(again using JavaFX's MediaPlayer class) for ensuring that the correct media
is being converted.

![Compile Screen](ffmpeg-prog-compile.png)

The compile screen allows concatenating several media files into a single
media file. It uses ffmpeg's concat filter.



## Wizard Interface ##

An interface designed and implemented by Chris Webb. This interface uses the familiar wizard
style to guide the user.
