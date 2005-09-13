#!/usr/bin/perl

package Test1;

use warnings;
use strict;

use Wx;
use base qw(Wx::App);	# von Wx:App erben

sub OnInit {
	my ( $self ) = shift;
	my ( $frame ) = Test1Frame->new();

	$frame->Show( 1 );
}

package Test1Frame;

use Wx;
use base qw(Wx::Frame);

use Wx qw(wxDefaultPosition);

sub new {
	my ( $self ) = shift->SUPER::new( 
		undef,		# parent fenster
		-1,		# ID
		'Der Titel',	# Titel
		wxDefaultPosition,
		[640, 480]	# Position
	);

	return $self;
}

package main;

my ( $app ) = Test1->new();
$app->MainLoop();

