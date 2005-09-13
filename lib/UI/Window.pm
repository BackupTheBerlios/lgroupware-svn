package UI::Window;

use strict;
use warnings;

use Wx;
use base qw( Wx::Window );

sub new {
	my( $class, %params ) = @_;
	
	my( $self ) = $class->SUPER::new( $params{'parent'}, $params{'id'} );

	return $self;
}

1;
