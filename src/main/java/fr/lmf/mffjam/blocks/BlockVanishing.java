package fr.lmf.mffjam.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.util.Direction;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.ToolType;

import javax.annotation.Nullable;

public class BlockVanishing extends Block
{

	private static final BooleanProperty ACTIVATED = BooleanProperty.create("activated");

	public BlockVanishing()
	{
		super(Block.Properties.create(Material.ROCK).notSolid().harvestLevel(1).harvestTool(ToolType.PICKAXE).sound(SoundType.STONE).hardnessAndResistance(5.0f, 20.f));
		this.setDefaultState(this.stateContainer.getBaseState().with(ACTIVATED, false));
	}

	@Override
	public BlockRenderType getRenderType(BlockState state)
	{
		return BlockRenderType.MODEL;
	}


	@Override
	public boolean isNormalCube(BlockState state, IBlockReader worldIn, BlockPos pos)
	{
		return true;
	}


	@OnlyIn(Dist.CLIENT)
	public boolean isSideInvisible(BlockState state, BlockState adjacentBlockState, Direction side)
	{

		if( !state.get(ACTIVATED) )
		{
			return false;
		}

		return side != Direction.DOWN;
	}

	@Override
	public VoxelShape getCollisionShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context)
	{
		if( state.get(ACTIVATED) )
		{
			return VoxelShapes.empty();
		}
		else
		{
			return VoxelShapes.fullCube();
		}
	}

	@Override
	protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder)
	{
		builder.add(ACTIVATED);
	}

	@Override
	public void neighborChanged(BlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos, boolean isMoving)
	{
		super.neighborChanged(state, worldIn, pos, blockIn, fromPos, isMoving);

		boolean blockPowered = worldIn.isBlockPowered(pos);
		if( blockPowered && !state.get(ACTIVATED) )
		{
			worldIn.setBlockState(pos, state.with(ACTIVATED, true));
			if( worldIn.isRemote )
			{
				worldIn.playSound(pos.getX(), pos.getY(), pos.getZ(), SoundEvents.BLOCK_PISTON_EXTEND, SoundCategory.BLOCKS, 1.0f, 1.0f, false);
			}
		}

		if( !blockPowered && state.get(ACTIVATED) )
		{
			worldIn.setBlockState(pos, state.with(ACTIVATED, false));
			if( worldIn.isRemote )
			{
				worldIn.playSound(pos.getX(), pos.getY(), pos.getZ(), SoundEvents.BLOCK_PISTON_CONTRACT, SoundCategory.BLOCKS, 1.0f, 1.0f, false);
			}
		}
	}


	@Override
	public boolean canConnectRedstone(BlockState state, IBlockReader world, BlockPos pos, @Nullable Direction side)
	{
		return true;
	}
}
