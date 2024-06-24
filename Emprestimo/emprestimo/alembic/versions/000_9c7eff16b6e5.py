"""000

Revision ID: 9c7eff16b6e5
Revises: 
Create Date: 2024-06-25 03:18:42.412199

"""
from alembic import op
import sqlalchemy as sa


# revision identifiers, used by Alembic.
revision = '9c7eff16b6e5'
down_revision = None
branch_labels = None
depends_on = None


def upgrade() -> None:
    # ### commands auto generated by Alembic - please adjust! ###
    op.create_table('loans',
    sa.Column('account_number', sa.BigInteger(), nullable=False),
    sa.Column('value', sa.String(length=256), nullable=False),
    sa.Column('status', sa.Enum('PENDING', 'APPROVED', 'DISAPPROVED', name='status'), nullable=False),
    sa.Column('validated_at', sa.DateTime(timezone=True), nullable=True),
    sa.Column('id', sa.UUID(), nullable=False),
    sa.Column('created_at', sa.DateTime(timezone=True), server_default=sa.text('now()'), nullable=False),
    sa.PrimaryKeyConstraint('id')
    )
    # ### end Alembic commands ###


def downgrade() -> None:
    # ### commands auto generated by Alembic - please adjust! ###
    op.drop_table('loans')
    # ### end Alembic commands ###