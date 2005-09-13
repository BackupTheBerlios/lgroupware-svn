package UI::App;

use strict;
use warnings;

use Wx;
use base 'Wx::App';

sub new {
	my( $class, %params ) = @_;
	my( $self ) = $class->SUPER::new();

	$self->SetFrame( mainframe => $params{'mainframe'} );

	$self->{'mainframe'}->Show( 1 );

	return $self;
}

sub SetFrame {
	my ( $self, $type, $frame ) = @_;
	$self->{$type} = $frame;
}

sub OnInit {
	my ( $self ) = shift;
}


1;
