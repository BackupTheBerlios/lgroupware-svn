package UI::BoxSizer;

use warnings;
use strict;

use Wx;
use base qw( Wx::BoxSizer );

sub new {
	my( $class, %params ) = @_;
	my( $self ) = $class->SUPER::new( $params{'direction'} );
	
	return $self;
}

1;
