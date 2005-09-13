#!/usr/bin/perl

package Test3;

use warnings;
use strict;

use Wx;
use base qw( Wx::App );	# von Wx:App erben

sub OnInit {
	my ( $self ) = shift;
	my ( $frame ) = Test3Frame->new();

	$frame->Show( 1 );
}

package Test3Frame;
use strict;
use Wx;
use base qw( Wx::Frame );

use Wx qw( wxDefaultPosition wxDefaultSize wxSP_3D wxDEFAULT_FRAME_STYLE wxNO_FULL_REPAINT_ON_RESIZE wxCLIP_CHILDREN );

sub new {
	my ( $self ) = shift->SUPER::new( 
		undef,		# parent fenster
		-1,		# ID
		'Der Titel',	# Titel
		wxDefaultPosition,
		[640, 480],	# Position
		wxDEFAULT_FRAME_STYLE | wxNO_FULL_REPAINT_ON_RESIZE | wxCLIP_CHILDREN
	);

	my ( $splitter1 ) = Wx::SplitterWindow->new( $self, -1, 
		wxDefaultPosition, wxDefaultSize, wxSP_3D | wxNO_FULL_REPAINT_ON_RESIZE | wxCLIP_CHILDREN);

	my ( $frame1 ) = Wx::Window->new( $splitter1, -1 );
	$frame1->SetBackgroundColour( Wx::Colour->new( 133, 133, 0 ) );
	$frame1->SetSize( 300, -1 );

	my ( $frame2 ) = Wx::Window->new( $splitter1, -1 );
	
	$splitter1->SplitVertically( $frame1, $frame2 );
	
	return $self;
}

package main;

my ( $app ) = Test3->new();
$app->MainLoop();

