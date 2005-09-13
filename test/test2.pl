#!/usr/bin/perl

package Test2;

use warnings;
use strict;

use Wx;
use base qw(Wx::App);	# von Wx:App erben

sub OnInit {
	my ( $self ) = shift;
	my ( $frame ) = Test2Frame->new();

	$frame->Show( 1 );
}

package Test2Frame;

use Wx;
use base qw(Wx::Frame);

use Wx qw(wxDefaultPosition wxHORIZONTAL wxGROW);

sub new {
	my ( $self ) = shift->SUPER::new( 
		undef,		# parent fenster
		-1,		# ID
		'Der Titel',	# Titel
		wxDefaultPosition,
		[640, 480]	# Position
	);

	$self->SetIcon( Wx::GetWxPerlIcon() );
	my ( $sizer ) = Wx::BoxSizer->new( wxHORIZONTAL );

	my ( $frame1 ) = Wx::Panel->new( $self, -1 );
	my ( $frame2 ) = Wx::Panel->new( $self, -1 );

	$frame1->SetBackgroundColour( Wx::Colour->new(133, 133, 0) );
	$frame1->SetSize( 300, -1 );
	
	$sizer->Add( $frame1, 0, wxGROW );
	$sizer->Add( $frame2, 2, wxGROW );

	$self->SetSizer( $sizer );

	return $self;
}

package main;

my ( $app ) = Test2->new();
$app->MainLoop();

