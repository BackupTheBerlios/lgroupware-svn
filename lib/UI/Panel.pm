package UI::Panel;

use strict;
use warnings;

use Wx;
use base qw( Wx::Panel );

sub new {
	my( $class, %params ) = @_;
	my( $self ) = $class->SUPER::new( $params{'parent'}, $params{'id'} );

	return $self;
}

1;
