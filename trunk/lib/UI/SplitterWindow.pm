package UI::SplitterWindow;

use strict;
use warnings;

use Wx;
use base qw( Wx::SplitterWindow );

sub new {
	my( $class, %params ) = @_;
	my( $self ) = $class->SUPER::new( $params{'parent'}, $params{'id'}, $params{'position'}, $params{'dimension'}, $params{'options'} );

	return $self;
}

1;
