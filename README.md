# Octatrack-LaunchControl-Midi-Router

An app IN PROGRESS to get midi from a Novation Launch Control via usb and send it to an octatrack (or any other device of your choosing!) via a usb to 5pin midi converter cable *or just a standard usb cable if your synth/sampler/whatever supports it).

Hopefully this program will run on a raspberry pi, allowing users to use a raspberry pi as a Usb Midi Host. (Tested Working only on MAC OS 10.10.1)

A Launch Control template file is provided, which controls mutes, balances and volumes for the octatracks 8 tracks
(You'll need to set the channels of each track on the OT, track 1 = channel 1, track 2 - channel 2 etc.. )

A known bug is crash when you change template on the Launchpad, so make sure you select the right template before connecting up.

Toggle Dump dumps midi data to console

