package net.minegate.fr.moreblocks.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.HorizontalFacingBlock;
import net.minecraft.block.ShapeContext;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.function.BooleanBiFunction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minegate.fr.moreblocks.block.entity.ISitBlock;

import java.util.stream.Stream;

public class StoolHighBlock extends HorizontalFacingBlock implements ISitBlock
{
    private static final DirectionProperty FACING = HorizontalFacingBlock.FACING;
    private static final VoxelShape        VOXEL_SHAPE_NORTH;
    private static final VoxelShape        VOXEL_SHAPE_SOUTH;
    private static final VoxelShape        VOXEL_SHAPE_WEST;
    private static final VoxelShape        VOXEL_SHAPE_EAST;

    public StoolHighBlock(Settings blockSettings)
    {
        super(blockSettings);
        setDefaultState(this.stateManager.getDefaultState().with(Properties.HORIZONTAL_FACING, Direction.NORTH));
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> stateManager)
    {
        stateManager.add(Properties.HORIZONTAL_FACING);
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView view, BlockPos pos, ShapeContext ctx)
    {
        Direction dir = state.get(FACING);
        switch (dir)
        {
            case NORTH:
                return VOXEL_SHAPE_NORTH;
            case SOUTH:
                return VOXEL_SHAPE_SOUTH;
            case EAST:
                return VOXEL_SHAPE_EAST;
            case WEST:
                return VOXEL_SHAPE_WEST;
            default:
                return VoxelShapes.fullCube();
        }
    }

    @Override
    public BlockState getPlacementState(ItemPlacementContext placementContext)
    {
        return getDefaultState().with(FACING, placementContext.getPlayerFacing().getOpposite());
    }

    static
    {
        VOXEL_SHAPE_NORTH = Stream.of(
                Block.createCuboidShape(3, 10, 3, 13, 12, 13),
                Block.createCuboidShape(4, 0, 4, 6, 10, 6),
                Block.createCuboidShape(10, 0, 4, 12, 10, 6),
                Block.createCuboidShape(10, 0, 10, 12, 10, 12),
                Block.createCuboidShape(4, 0, 10, 6, 10, 12)).reduce((v1, v2) -> VoxelShapes.combineAndSimplify(v1, v2, BooleanBiFunction.OR)).get();
        VOXEL_SHAPE_SOUTH = Stream.of(
                Block.createCuboidShape(3, 10, 3, 13, 12, 13),
                Block.createCuboidShape(4, 0, 4, 6, 10, 6),
                Block.createCuboidShape(10, 0, 4, 12, 10, 6),
                Block.createCuboidShape(10, 0, 10, 12, 10, 12),
                Block.createCuboidShape(4, 0, 10, 6, 10, 12)).reduce((v1, v2) -> VoxelShapes.combineAndSimplify(v1, v2, BooleanBiFunction.OR)).get();
        VOXEL_SHAPE_WEST = Stream.of(
                Block.createCuboidShape(3, 10, 3, 13, 12, 13),
                Block.createCuboidShape(4, 0, 4, 6, 10, 6),
                Block.createCuboidShape(10, 0, 4, 12, 10, 6),
                Block.createCuboidShape(10, 0, 10, 12, 10, 12),
                Block.createCuboidShape(4, 0, 10, 6, 10, 12)).reduce((v1, v2) -> VoxelShapes.combineAndSimplify(v1, v2, BooleanBiFunction.OR)).get();
        VOXEL_SHAPE_EAST = Stream.of(
                Block.createCuboidShape(3, 10, 3, 13, 12, 13),
                Block.createCuboidShape(4, 0, 4, 6, 10, 6),
                Block.createCuboidShape(10, 0, 4, 12, 10, 6),
                Block.createCuboidShape(10, 0, 10, 12, 10, 12),
                Block.createCuboidShape(4, 0, 10, 6, 10, 12)).reduce((v1, v2) -> VoxelShapes.combineAndSimplify(v1, v2, BooleanBiFunction.OR)).get();
    }

    @Override
    public Vec3d getSitPosition()
    {
        return new Vec3d(0.5D, 0.55D, 0.5D);
    }
}